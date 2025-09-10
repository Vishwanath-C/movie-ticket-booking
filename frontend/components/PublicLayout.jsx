import { Box } from "@mui/material";
import AppBarComponent from "./AppBarComponent";
import { Outlet } from "react-router-dom";

const PublicLayout = () => {
  return (
    <Box>
      <AppBarComponent isLoggedIn={false} />
      <Box sx={{ p: 3, mt: "64px" }}>
        <Outlet /> {/* Nested routes render here */}
      </Box>
    </Box>
  );
};

export default PublicLayout;
