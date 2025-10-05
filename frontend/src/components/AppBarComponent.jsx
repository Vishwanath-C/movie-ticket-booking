import MenuIcon from "@mui/icons-material/Menu";
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  Typography,
  useMediaQuery,
} from "@mui/material";
import { useState } from "react";

const AppBarComponent = ({ isLoggedIn, setIsLoggedIn, onMenuToggle }) => {
  const role = localStorage.getItem("role");
  const [logoutOpen, setLogoutOpen] = useState(false);
  const isMobile = useMediaQuery("(max-width:600px)"); // mobile detection

  const handleLogoutConfirm = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    if (setIsLoggedIn) setIsLoggedIn(false);
    setLogoutOpen(false);
  };

  return (
    <>
      <Box
        className="w-100 fixed-top shadow-sm"
        sx={{
          backgroundColor: "#1976d2",
          color: "white",
          zIndex: 1000,
          minHeight: "90px",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          px: 2,
        }}
      >
        {/* Hamburger menu for mobile */}
        {isMobile && isLoggedIn && (
          <IconButton color="inherit" onClick={onMenuToggle}>
            <MenuIcon />
          </IconButton>
        )}

        {/* Role badge */}
        {isLoggedIn && !isMobile && (
          <Box
            sx={{
              bgcolor: "#ffc107",
              color: "#212529",
              fontWeight: 600,
              px: 3,
              py: 1,
              borderRadius: "0.5rem",
              boxShadow: "0 2px 5px rgba(0,0,0,0.2)",
            }}
          >
            Logged in as: {role || "Guest"}
          </Box>
        )}

        {/* Center title */}
        <Typography
          sx={{
            fontSize: "1.6rem",
            fontWeight: "bold",
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            textShadow: "1px 1px 2px rgba(0,0,0,0.2)",
          }}
        >
          🎬 Movie Ticket Booking System
        </Typography>

        {/* Logout button */}
        {isLoggedIn && !isMobile && (
          <Button
            sx={{
              borderRadius: "0.5rem",
              fontWeight: "bold",
              boxShadow: "0 2px 5px rgba(0,0,0,0.2)",
              backgroundColor: "white",
              color: "#1976d2",
              "&:hover": { backgroundColor: "#f0f0f0" },
            }}
            onClick={() => setLogoutOpen(true)}
          >
            Logout
          </Button>
        )}
      </Box>

      {/* Logout confirmation dialog */}
      <Dialog
        open={logoutOpen}
        onClose={() => setLogoutOpen(false)}
        sx={{ "& .MuiPaper-root": { borderRadius: 3, minWidth: 300 } }}
      >
        <DialogTitle sx={{ textAlign: "center", fontWeight: "bold" }}>Logout</DialogTitle>
        <DialogContent>
          <DialogContentText sx={{ textAlign: "center" }}>
            Are you sure you want to logout?
          </DialogContentText>
        </DialogContent>
        <DialogActions sx={{ justifyContent: "center", gap: 2, pb: 2 }}>
          <Button variant="outlined" onClick={() => setLogoutOpen(false)}>
            Cancel
          </Button>
          <Button variant="contained" color="error" onClick={handleLogoutConfirm}>
            Logout
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default AppBarComponent;
