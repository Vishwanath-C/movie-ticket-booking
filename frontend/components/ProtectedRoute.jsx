import { Box } from "@mui/material";
import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = ({ setIsLoggedIn }) => {
  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  return (
    <Box sx={{ display: "flex", minHeight: "100vh" }}>
      {/* Sidebar + main content */}
      <Outlet />
    </Box>
  );
};

export default ProtectedRoute;
