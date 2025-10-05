import EventBusyIcon from "@mui/icons-material/EventBusy";
import {
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Container,
    Divider,
    Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../api";
import EmptyState from "./EmptyState";
import TicketList from "./TicketList";

const FinishedBookings = () => {
    const token = localStorage.getItem("token");
    const [finishedTickets, setFinishedTickets] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [fetchError, setFetchError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchFinishedTickets();
    }, []);

    const fetchFinishedTickets = async () => {
        setIsLoading(true);
        setFetchError(null);
        try {
            const response = await apiClient.get("/tickets/finished", {
                headers: { Authorization: `Bearer ${token}` },
            });
            setFinishedTickets(response.data);
        } catch (error) {
            console.error("Error fetching finished tickets:", error);
            setFetchError("Failed to load your ticket history. Please try again.");
        } finally {
            setIsLoading(false);
        }
    };

    if (isLoading) {
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

    if (fetchError) {
        return (
            <Container
                maxWidth="md"
                sx={{
                    mt: 8,
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                }}
            >
                <Card sx={{ maxWidth: 400, p: 3, textAlign: "center" }}>
                    <Typography variant="h6" color="error" fontWeight="bold">
                        ⚠️ Error
                    </Typography>
                    <Typography variant="body2" color="text.secondary" mt={1}>
                        {fetchError}
                    </Typography>
                    <CardContent>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={fetchFinishedTickets}
                        >
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
                {finishedTickets.length > 0 ? (
                    <TicketList tickets={finishedTickets} />
                ) : (
                    <EmptyState
                        heading="No ticket history"
                        icon={EventBusyIcon}
                        buttonText="Browse Movies"
                        onButtonClick={() => navigate("/app/movies")}
                    />
                )}
            </Box>

            <Divider sx={{ my: 3 }} />
        </Container>
    );
};

export default FinishedBookings;
