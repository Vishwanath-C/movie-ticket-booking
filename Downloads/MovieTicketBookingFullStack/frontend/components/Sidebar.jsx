import { Drawer, List, ListItem, ListItemText, Toolbar } from '@mui/material';
import { useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import { useNavigate, useLocation } from 'react-router-dom';

const drawerWidth = 160;

function Sidebar({ setIsLoggedIn }) {
    const navigate = useNavigate();
    const location = useLocation();
    const [show, setShow] = useState(false);
    const role = localStorage.getItem('role');

    const handleOpen = () => setShow(true);
    const handleClose = () => setShow(false);

    const adminItems = [
        { label: 'Theatres', path: '/app/theatres' },
        { label: 'Movies', path: '/app/movies' },
        { label: 'Logout', action: 'logout' },
    ];

    const userItems = [
        { label: 'Movies', path: '/app/movies' },
        { label: 'MyBookings', path: '/app/tickets' },
        { label: 'Logout', action: 'logout' },
    ];

    const handleLogout = () => {
        setIsLoggedIn(false);
        localStorage.removeItem('token');
        navigate('/');
    };

    const handleItemClick = (item) => {
        if (item.action === 'logout') {
            handleOpen();
        } else if (item.path) {
            navigate(item.path);
        }
    };

    const items = role === 'ADMIN' ? adminItems : userItems;

    return (
        <><Drawer
            variant="permanent"
            sx={{
                width: drawerWidth,
                flexShrink: 0,
                [`& .MuiDrawer-paper`]: {
                    width: drawerWidth,
                    boxSizing: 'border-box',
                    top: '96px',
                },
            }}
        >
            <Toolbar />
            <List>

                {items.map((item) => {
                    const isActive = item.path && location.pathname.startsWith(item.path);

                    return (<ListItem
                        button
                        key={item.label}
                        onClick={() => handleItemClick(item)}
                        sx={{
                            backgroundColor: isActive ? '#e3f2fd' : 'transparent',
                            color: isActive ? '#1976d2' : 'inherit',
                            fontWeight: isActive ? 'bold' : 'normal',
                            borderLeft: isActive ? '4px solid #1976d2' : '4px solid transparent',
                        }}
                    >
                        <ListItemText primary={item.label} />
                    </ListItem>);
                })}

            </List>
        </Drawer>
            <Modal show={show} onHide={() => setShow(false)}>
                <Modal.Body className='fw-bold fs-5 text-center p-4'>Are you sure you want to logout?</Modal.Body>
                <Modal.Footer className='justify-content-center'>
                    <Button variant="secondary" onClick={handleClose}>Cancel</Button>
                    <Button variant="primary" onClick={handleLogout}>Logout</Button>
                </Modal.Footer>
            </Modal></>
    );
}

export default Sidebar;