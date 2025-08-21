import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';

const DashboardLayout = () => {
    const drawerWidth = 160;

    return (
        <div style={{ display: 'flex' }}>
            {/* <Sidebar setIsLoggedIn={setIsLoggedIn} /> */}
            <main style={{ flexGrow: 1, padding: '16px' }}>
                <Outlet />
            </main>
        </div>
    );
};

export default DashboardLayout;
