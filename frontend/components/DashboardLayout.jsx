import { Box } from "@mui/material";
import AppBarComponent from "./AppBarComponent";
import Sidebar from "./Sidebar";
import { Outlet } from "react-router-dom";

const Layout = ({ setIsLoggedIn }) => {
    const SIDEBAR_WIDTH = 240;

    return (
        <Box sx={{ display: "flex" }}>
            <Sidebar setIsLoggedIn={setIsLoggedIn} width={SIDEBAR_WIDTH} />

            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    
                    mt: "90px", // top offset for AppBar
                    minHeight: `calc(100vh - 90px)`,
                    px: 3,
                }}
            >
                <AppBarComponent isLoggedIn={true} setIsLoggedIn={setIsLoggedIn} />
                <Outlet />
            </Box>
        </Box>
    );
};

export default Layout;
