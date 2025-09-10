import {
    Box,
    CircularProgress,
    Container,
    Divider,
    Paper,
    Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../src/api";
import TicketList from "./TicketList";
import EmptyState from "./EmptyState";
import EventBusyIcon from '@mui/icons-material/EventBusy';


const FinishedBookings = () => {
    const token = localStorage.getItem("token");
    const [ticketsFinished, setTicketsFinished] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        handleShowTickets();
    }, []);

    const handleShowTickets = async () => {
        try {
            const response = await apiClient.get("/tickets/finished", {
                headers: { Authorization: `Bearer ${token}` },
            });

            setTicketsFinished(response.data);

        } catch (error) {
            console.log(error);
        } finally {
            setLoading(false);
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
            {/* <Paper
        elevation={3}
        sx={{ p: 2, borderRadius: 3, backgroundColor: "#f9fafb", mb: 3 }}
      >
        <Typography variant="h5" fontWeight="bold" align="center">
          FINISHED
        </Typography>
      </Paper> */}
            <Box sx={{ mb: 5 }}>
                {ticketsFinished.length > 0 ? (
                    <TicketList tickets={ticketsFinished} />
                ) : (
                    <Typography align="center" color="text.secondary">
                        <EmptyState
                            heading="No ticket history"

                            icon={EventBusyIcon}
                            buttonText="Browse Movies"
                            onButtonClick={() => navigate("/app/movies")}
                        />
                    </Typography>
                )}
            </Box>

            <Divider sx={{ my: 3 }} />

            {/* Finished Tickets
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
      </Box> */}
        </Container>
    );
};

export default FinishedBookings;
