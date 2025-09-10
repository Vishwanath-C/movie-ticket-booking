import { LoadingButton } from "@mui/lab";
import {
    Box,
    Container,
    Divider,
    Typography
} from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../src/api";
import EmptyState from "./EmptyState";
import TicketList from "./TicketList";

const UpcomingBookings = () => {
    const token = localStorage.getItem("token");
    const [ticketsUpcoming, setTicketsUpcoming] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        handleShowTickets();
    }, []);

    const handleShowTickets = async () => {
        try {
            const response = await apiClient.get("/tickets/upcoming", {
                headers: { Authorization: `Bearer ${token}` },
            });

            setTicketsUpcoming(response.data);
          

        } catch (error) {
            if (error.response?.data?.fieldErrors) {     
                setErrors(error.response.data.fieldErrors);
            } else {
                alert(error.response?.data?.message || "Something went wrong");
            }
        } finally {
            setLoading(false);
        }
    };



    if (loading) {
        return (
            <Container maxWidth="md" sx={{ mt: 8 }}>
                <Box
                    display="flex"
                    flexDirection="column"
                    alignItems="center"
                    justifyContent="center"
                    gap={2} // spacing between button and text
                    minHeight="50vh" // vertically center
                >
                    <LoadingButton
                        loading
                        variant="outlined"
                        sx={{ width: "200px", height: "50px" }}
                    >
                        Loading your tickets...
                    </LoadingButton>

                    <Typography variant="h6" color="text.secondary" mt={2}>
                        Please wait while we fetch your tickets
                    </Typography>
                </Box>
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
                    UPCOMING
                </Typography>
            </Paper> */}
            <Box sx={{ mb: 5 }}>
                {ticketsUpcoming.length > 0 ? (
                    <TicketList tickets={ticketsUpcoming} />
                ) : (
                    // <Typography align="center" color="text.secondary">
                    <EmptyState
                        heading="No Upcoming Tickets"


                        buttonText="Browse Movies"
                        onButtonClick={() => navigate("/app/movies")}
                    />
                    // </Typography>
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

export default UpcomingBookings;
