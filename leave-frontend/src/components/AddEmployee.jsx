import { useState } from 'react';
import axios from 'axios';
import { API_BASE_URL } from '../config';

function AddEmployee() {
  const [form, setForm] = useState({ name: '', email: '', department: '', leaveBalance: '' });
  const [message, setMessage] = useState('');

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post(`${API_BASE_URL}/api/employees`, form)
      .then(() => {
        setMessage('Employee added');
        setForm({ name: '', email: '', department: '', leaveBalance: '' });
      })
      .catch(() => setMessage('Error adding employee'));
  };

  return (
    <div>
      <h2>Add Employee</h2>
      <form onSubmit={handleSubmit}>
        <input name="name" placeholder="Name" value={form.name} onChange={handleChange} />
        <input name="email" placeholder="Email" value={form.email} onChange={handleChange} />
        <input name="department" placeholder="Department" value={form.department} onChange={handleChange} />
        <input name="leaveBalance" placeholder="Leave Balance" value={form.leaveBalance} onChange={handleChange} />
        <button type="submit">Add</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}

export default AddEmployee;