import { Box, Alert, Button } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import Ticket from "./Ticket";

const TicketPage = () => {
  const { state } = useLocation();
  const ticket = state?.ticket;
  const navigate = useNavigate();

  if (!ticket) return <div>No ticket data available</div>;

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      
      gap={3} // space between Alert, Ticket, and Button
      mt={4}  // margin from navbar
    >
      {/* Success message */}
      <Alert severity="success" sx={{ width: "100%", maxWidth: 400, textAlign: "center" }}>
         Ticket booked successfully!
      </Alert>

      {/* Ticket Card */}
      <Ticket ticket={ticket} />

      {/* Go to My Tickets button */}
      <Button
        variant="contained"
        color="primary"
        onClick={() => navigate("/app/tickets/upcoming")}
      >
        Go to My Tickets
      </Button>
    </Box>
  );
};

export default TicketPage;
