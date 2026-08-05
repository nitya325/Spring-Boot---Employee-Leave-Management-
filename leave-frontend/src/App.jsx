import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import EmployeeList from './components/EmployeeList';
import ApplyLeave from './components/ApplyLeave';
import LeaveList from './components/LeaveList';
import AddEmployee from './components/AddEmployee';

function App() {
  return (
    <BrowserRouter>
      <h1>Employee Leave Management</h1>
      <nav>
        <Link to="/">Employees</Link> | <Link to="/apply">Apply Leave</Link> | <Link to="/leaves">Leave Requests</Link>
      </nav>
      <Routes>
        <Route path="/" element={<><AddEmployee /><EmployeeList /></>} />
        <Route path="/apply" element={<ApplyLeave />} />
        <Route path="/leaves" element={<LeaveList />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;