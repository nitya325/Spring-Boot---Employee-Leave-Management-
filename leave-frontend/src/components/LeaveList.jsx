import { useState, useEffect } from 'react';
import axios from 'axios';

function LeaveList() {
  const [leaves, setLeaves] = useState([]);
  const [employeeId, setEmployeeId] = useState('1');

  const fetchLeaves = () => {
    axios.get(`http://localhost:8080/api/leaves/employee/${employeeId}`)
      .then(res => setLeaves(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    fetchLeaves();
  }, []);

  const updateStatus = (id, status) => {
    axios.put(`http://localhost:8080/api/leaves/${id}/status?status=${status}`)
      .then(() => fetchLeaves())
      .catch(err => alert(err.response?.data?.error || 'Error'));
  };

  return (
    <div>
      <h2>Leave Requests</h2>
      <input value={employeeId} onChange={e => setEmployeeId(e.target.value)} />
      <button onClick={fetchLeaves}>Load</button>
      <table border="1">
        <thead>
          <tr>
            <th>ID</th><th>Employee</th><th>Start</th><th>End</th>
            <th>Reason</th><th>Status</th><th>Action</th>
          </tr>
        </thead>
        <tbody>
          {leaves.map(lv => (
            <tr key={lv.id}>
              <td>{lv.id}</td>
              <td>{lv.employeeName}</td>
              <td>{lv.startDate}</td>
              <td>{lv.endDate}</td>
              <td>{lv.reason}</td>
              <td>{lv.status}</td>
              <td>
                {lv.status === 'PENDING' && (
                  <>
                    <button onClick={() => updateStatus(lv.id, 'APPROVED')}>Approve</button>
                    <button onClick={() => updateStatus(lv.id, 'REJECTED')}>Reject</button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default LeaveList;