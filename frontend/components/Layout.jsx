import { Box } from "@mui/material";
import AppBarComponent from "./AppBarComponent";
import Sidebar from "./Sidebar";
import { Outlet } from "react-router-dom";

const Layout = ({ setIsLoggedIn }) => {
  const SIDEBAR_WIDTH = 240;

  return (
    <Box sx={{ display: "flex", minHeight: "100vh" }}>
      {/* Sidebar */}
      <Sidebar setIsLoggedIn={setIsLoggedIn} width={SIDEBAR_WIDTH} />

      {/* Main content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          mt: "120px", // offset for fixed AppBar
          minHeight: `calc(100vh - 90px)`,
          px: 3
          
        }}
      >
        {/* Topbar fixed */}
        <AppBarComponent isLoggedIn={true} setIsLoggedIn={setIsLoggedIn} />

        {/* Nested routes render here */}
        <Outlet />
      </Box>
    </Box>
  );
};

export default Layout;
