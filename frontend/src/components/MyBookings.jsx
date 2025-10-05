import {
  Box,
  CircularProgress,
  Container,
  Divider,
  Paper,
  Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import apiClient from "../api";
import TicketList from "./TicketList";

const MyBookings = () => {
  const token = localStorage.getItem("token");
  const [ticketsUpcoming, setTicketsUpcoming] = useState([]);
  const [ticketsFinished, setTicketsFinished] = useState([]);
  const [loading, setLoading] = useState(true); // 🔹 new

  useEffect(() => {
    handleShowTickets();
  }, []);

  const handleShowTickets = async () => {
    try {
      const response = await apiClient.get("/tickets", {
        headers: { Authorization: `Bearer ${token}` },
      });

      const allTickets = response.data;
      const finished = [];
      const upcoming = [];
      const currentDate = new Date();

      allTickets.forEach((ticket) => {
        const dateTimeString = `${ticket.showDate}T${ticket.showTime}`;
        const showDateTime = new Date(dateTimeString);

        if (showDateTime < currentDate) finished.push(ticket);
        else upcoming.push(ticket);
      });

      setTicketsUpcoming(upcoming);
      setTicketsFinished(finished);
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false); // 🔹 stop loading after request
    }
  };

  if (loading) {
    return (
      <Container
        maxWidth="md"
        sx={{
          mt: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          gap: 3,
        }}
      >
        <CircularProgress />
        <Typography variant="h6" color="text.secondary">
          Loading your tickets...
        </Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
      {/* Upcoming Tickets */}
      <Paper
        elevation={3}
        sx={{ p: 2, borderRadius: 3, backgroundColor: "#f9fafb", mb: 3 }}
      >
        <Typography variant="h5" fontWeight="bold" align="center">
          UPCOMING
        </Typography>
      </Paper>
      <Box sx={{ mb: 5 }}>
        {ticketsUpcoming.length > 0 ? (
          <TicketList tickets={ticketsUpcoming} />
        ) : (
          <Typography align="center" color="text.secondary">
            No upcoming tickets.
          </Typography>
        )}
      </Box>

      <Divider sx={{ my: 3 }} />

      {/* Finished Tickets */}
      <Paper
        elevation={3}
        sx={{ p: 2, borderRadius: 3, backgroundColor: "#f9fafb", mb: 3 }}
      >
        <Typography variant="h5" fontWeight="bold" align="center">
          FINISHED
        </Typography>
      </Paper>
      <Box sx={{ mb: 5 }}>
        {ticketsFinished.length > 0 ? (
          <TicketList tickets={ticketsFinished} />
        ) : (
          <Typography align="center" color="text.secondary">
            No finished tickets.
          </Typography>
        )}
      </Box>
    </Container>
  );
};

export default MyBookings;
