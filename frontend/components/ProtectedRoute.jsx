import { Box, Toolbar } from '@mui/material';
import Sidebar from './Sidebar';

const ProtectedRoute = ({ setIsLoggedIn, children }) => {
    const role = localStorage.getItem('role');

    return (
        <Box sx={{ display: 'flex' }}>
            <Sidebar  setIsLoggedIn={setIsLoggedIn}/>
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <Toolbar />
                {children}
            </Box>
        </Box>
    );
};



export default ProtectedRoute;