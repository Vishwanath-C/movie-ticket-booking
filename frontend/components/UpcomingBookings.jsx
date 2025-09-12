import { LoadingButton } from "@mui/lab";
import { Box, Button, Card, CardContent, Container, Divider, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../src/api";
import EmptyState from "./EmptyState";
import TicketList from "./TicketList";

const UpcomingBookings = () => {
    const token = localStorage.getItem("token");
    const [upcomingTickets, setUpcomingTickets] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [fetchError, setFetchError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchUpcomingTickets();
    }, []);

    const fetchUpcomingTickets = async () => {
        setIsLoading(true);
        setFetchError(null);
        try {
            const response = await apiClient.get("/tickets/upcoming", {
                headers: { Authorization: `Bearer ${token}` },
            });
            setUpcomingTickets(response.data);
        } catch (error) {
            console.error("Error fetching upcoming tickets:", error);
            setFetchError(error.response?.data?.message || "Failed to load your upcoming tickets. Please try again.");
        } finally {
            setIsLoading(false);
        }
    };

    if (isLoading) {
        return (
            <Container maxWidth="md" sx={{ mt: 8 }}>
                <Box
                    display="flex"
                    flexDirection="column"
                    alignItems="center"
                    justifyContent="center"
                    gap={2}
                    minHeight="50vh"
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

    if (fetchError) {
        return (
            <Container maxWidth="md" sx={{ mt: 8, display: "flex", justifyContent: "center", alignItems: "center" }}>
                <Card sx={{ maxWidth: 400, p: 3, textAlign: "center" }}>
                    <Typography variant="h6" color="error" fontWeight="bold">
                        ⚠️ Error
                    </Typography>
                    <Typography variant="body2" color="text.secondary" mt={1}>
                        {fetchError}
                    </Typography>
                    <CardContent>
                        <Button variant="contained" onClick={fetchUpcomingTickets}>
                            Retry
                        </Button>
                    </CardContent>
                </Card>
            </Container>
        );
    }

    return (
        <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
            <Box sx={{ mb: 5 }}>
                {upcomingTickets.length > 0 ? (
                    <TicketList tickets={upcomingTickets} />
                ) : (
                    <EmptyState
                        heading="No Upcoming Tickets"
                        buttonText="Browse Movies"
                        onButtonClick={() => navigate("/app/movies")}
                    />
                )}
            </Box>

            <Divider sx={{ my: 3 }} />
        </Container>
    );
};

export default UpcomingBookings;
