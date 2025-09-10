import { Box, Button, Container, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";

const HomePage = ({ isLoggedIn }) => {
  const navigate = useNavigate();

  const handleRegisterClick = () => navigate("/register");
  const handleLoginClick = () => navigate("/login");

  return (
    <Container maxWidth="sm" sx={{ mt: 5, textAlign: "center" }}>
      <Typography variant="h4" fontWeight="bold" gutterBottom>
        🎬 Welcome to Movie Ticket Booking
      </Typography>

      {!isLoggedIn && (
        <Box sx={{ mt: 4, display: "flex", flexDirection: "column", gap: 2 }}>
          <Button
            variant="contained"
            color="primary"
            size="large"
            onClick={handleRegisterClick}
          >
            Register
          </Button>
          <Button
            variant="outlined"
            color="primary"
            size="large"
            onClick={handleLoginClick}
          >
            Login
          </Button>
        </Box>
      )}

      {isLoggedIn && (
        <Typography variant="h6" sx={{ mt: 4 }}>
          You are logged in. Navigate to the dashboard to manage movies and bookings.
        </Typography>
      )}
    </Container>
  );
};

export default HomePage;
