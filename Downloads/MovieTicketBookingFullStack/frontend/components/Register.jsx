import apiClient from "../src/api";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Register = () => {
    const navigate = useNavigate();
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log("In register");
            await apiClient.post('/auth/register', {
                firstName, lastName, email, password
            });
            console.log("After");
            navigate('/');
        } catch (error) {

        }
    };

    return (
        <div className="container mt-5 w-50 bg-info p-5">
            <h2 className="text-success text-center mb-3">Register</h2>
            <form className="d-block" onSubmit={handleSubmit}>
                <input type="text" className="form-control mb-3" value={firstName} onChange={e => setFirstName(e.target.value)} placeholder="Enter first name" required />
                <input type="text" className="form-control mb-3" value={lastName} onChange={e => setLastName(e.target.value)} placeholder="Enter second name" required />
                <input type="email" className="form-control mb-3" value={email} onChange={e => setEmail(e.target.value)} placeholder="Enter email" required />
                <input type="password" className="form-control mb-3" value={password} onChange={e => setPassword(e.target.value)} placeholder="Enter password" required />
                {/* <input type="text" className="form-control mb-3" placeholder="Enter password again" required /> */}
                <button type="submit" className="d-block mx-auto btn btn-primary">Register</button>
            </form>
        </div>
    );
};

export default Register;