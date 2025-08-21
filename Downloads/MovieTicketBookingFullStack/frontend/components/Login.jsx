import apiClient from '../src/api';
import { jwtDecode } from 'jwt-decode';
import { useState } from "react";
import { useNavigate } from 'react-router-dom';


const Login = ({ setIsLoggedIn }) => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);
    
    const handleSubmit = async (e) => {
        e.preventDefault();

        console.log("Login : ", email, password);

        try {
            console.log("Login : ", email, password);
            const res = await apiClient.post('/auth/login', { email, password });
            const token = res.data.accessToken;

            if (!token) {
                console.error("No token received from backend.");
            }

            localStorage.setItem("token", token);

            const decodedJWT = jwtDecode(token);
            const role = decodedJWT.role;

            localStorage.setItem("role", role);

            setIsLoggedIn(true);
            navigate('/app');
        }
        catch (e) {
            setError(true);
            console.log("Error ", error);
        }
    };

    return (
        <div className="container mt-5 w-50 bg-info p-4">
            <h2 className="text-center text-green">Login</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <label className="p-2">Email</label>
                    <input type="email" className="form-control" value={email} onChange={e => setEmail(e.target.value)} required />
                </div>
                <div className="mb-4">
                    <label className="p-2">Password</label>
                    <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required />
                </div>
                <button type="submit" className="btn btn-primary d-block mx-auto" >Login</button>
                {error && <div className="alert alert-danger mt-4 text-center">
                    <h5>Credentials are wrong</h5> <button className='btn btn-primary mt-2'
                    onClick={() => {
                        localStorage.removeItem('token');
                        setError(false);
                        setEmail('');
                        setPassword('');
                        navigate('/login');
                    }}>Retry</button>
                </div>}
            </form>
        </div>
    );

};

export default Login;