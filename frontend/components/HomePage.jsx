import { Box, CssBaseline, Toolbar } from '@mui/material';
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import Sidebar from "./Sidebar";

const HomePage = ({ isLoggedIn, setIsLoggedIn }) => {

    const navigate = useNavigate();
    const location = useLocation();
    const isDefaultRoute = location.pathname === '/';

    // const role = localStorage.getItem('role');

    const handleRegisterClick = () => {
        navigate('/register');
    };
    const handleLoginClick = () => {
        navigate('/login');
    };

    return (
        <div>
            <Box sx={{ display: 'flex' }}>

                <CssBaseline />

                {isLoggedIn && <Sidebar setIsLoggedIn={setIsLoggedIn} />}

                {/* Main Content */}
                <Box component="main" sx={{ flexGrow: 1, p: 3 }} >
                    <Toolbar />
                    <Outlet />
                    {isDefaultRoute && (
                        <div className="d-flex flex-column align-items-center">

                            <button className="btn btn-primary w-50 mb-4"
                                onClick={() => handleRegisterClick()}>Register</button>

                            <button className="btn btn-primary w-50"
                                onClick={() => handleLoginClick()}>Login</button>
                        </div>
                    )}
                </Box>
            </Box >

        </div>
    );
};

export default HomePage;