import { useState } from 'react';
import axios from 'axios';

function ApplyLeave() {
  const [form, setForm] = useState({
    employeeId: '',
    startDate: '',
    endDate: '',
    reason: ''
  });
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post(`${API_BASE_URL}/api/employees`, form)
      .then(() => setMessage('Leave applied successfully'))
      .catch(err => setMessage('Error: ' + err.response?.data?.error || 'Failed'));
  };

  return (
    <div>
      <h2>Apply Leave</h2>
      <form onSubmit={handleSubmit}>
        <input
          name="employeeId"
          placeholder="Employee ID"
          value={form.employeeId}
          onChange={handleChange}
        />
        <input
          type="date"
          name="startDate"
          value={form.startDate}
          onChange={handleChange}
        />
        <input
          type="date"
          name="endDate"
          value={form.endDate}
          onChange={handleChange}
        />
        <input
          name="reason"
          placeholder="Reason"
          value={form.reason}
          onChange={handleChange}
        />
        <button type="submit">Apply</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}

export default ApplyLeave;