import { Box } from "@mui/material";
import { useState } from "react";
import { Outlet } from "react-router-dom";
import AppBarComponent from "./AppBarComponent";
import Sidebar from "./Sidebar";

const DashboardLayout = ({ setIsLoggedIn, isLoggedIn }) => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  // Toggle sidebar open/close (for mobile)
  const handleMenuToggle = () => setIsSidebarOpen((prev) => !prev);

  return (
    <Box sx={{ display: "flex" }}>
      {/* Sidebar receives open state and toggle callback */}
      <Sidebar
        setIsLoggedIn={setIsLoggedIn}
        open={isSidebarOpen}
        onMenuToggle={handleMenuToggle}
      />

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          mt: "90px",
          minHeight: `calc(100vh - 90px)`,
          px: { xs: 2, sm: 3 },
          overflow: "auto",
        }}
      >
        {/* AppBar receives toggle callback to open sidebar on mobile */}
        <AppBarComponent
          isLoggedIn={isLoggedIn}
          setIsLoggedIn={setIsLoggedIn}
          onMenuToggle={handleMenuToggle}
        />
        <Outlet />
      </Box>
    </Box>
  );
};

export default DashboardLayout;
