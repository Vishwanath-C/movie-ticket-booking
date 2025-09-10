import {
  Box,
  Button,
  Card,
  CardContent,
  CardHeader,
  Chip,
  Grid,
  Stack,
  Typography,
  CircularProgress,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import apiClient from "../src/api";

const Bookings = () => {
  const location = useLocation();
  const { movie } = location.state || {}; // Get movie object from navigation state

  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");
  const navigate = useNavigate();
  const { movieId } = useParams();

  // Movie shows for the selected date
  const [movieShows, setMovieShows] = useState([]);

  // Available dates from backend
  const [availableDates, setAvailableDates] = useState([]);

  // Selected date in the date selector
  const [selectedDate, setSelectedDate] = useState(() => {
    // If upcoming shows exist, select the first available date automatically
    if (movie?.isUpcoming && movie?.availableShowDates?.length > 0) {
      return movie.availableShowDates[0];
    }
    // Otherwise default to today
    return new Date().toISOString().split("T")[0];
  });

  // Error handling for fetch
  const [error, setError] = useState(null);

  // Determines whether there are any shows for the selected date
  const noShows = !movieShows.some((group) => group.length > 0);

  /**
   * Generates an array of upcoming dates to show in the date selector.
   * Starts from today for running shows or first available date for upcoming shows.
   */
  const getUpcomingDates = (numDays = 7) => {
    const dates = [];
    let startDate;

    if (movie.isCurrentlyRunning) {
      startDate = new Date(); // Today for running movies
    } else if (movie?.isUpcoming && movie?.availableShowDates?.length > 0) {
      startDate = new Date(movie.availableShowDates[0]); // First upcoming date
    } else {
      startDate = new Date(); // Default fallback
    }

    for (let i = 0; i < numDays; i++) {
      const date = new Date(startDate);
      date.setDate(startDate.getDate() + i);
      dates.push(date);
    }

    return dates;
  };

  // Set available dates from the movie object and auto-select first date for upcoming shows
  useEffect(() => {
    if (movie?.availableShowDates) {
      setAvailableDates(movie.availableShowDates);

      if (movie.isUpcoming && movie.availableShowDates.length > 0) {
        setSelectedDate(movie.availableShowDates[0]);
      }
    }
  }, [movie]);

  // Fetch shows whenever selected date or movieId changes
  useEffect(() => {
    if (movieId && selectedDate) {
      fetchMovieShows(selectedDate);
    }
  }, [movieId, selectedDate]);

  // Fetch shows for a specific date
  const fetchMovieShows = async (date) => {
    setError(null);
    try {
      const response = await apiClient.get(
        `/movies/${movieId}/shows/date/${date}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMovieShows(response.data);
    } catch (err) {
      console.error("Error fetching movie shows:", err);
      setError("Failed to load movie shows. Please try again later.");
    }
  };

  // Navigate to seat layout page for selected show
  const handleShowSeatLayout = (selectedMovieShow) => {
    navigate("/app/seatlayout", { state: { selectedMovieShow } });
  };

  return (
    <Box
      sx={{
        mt: 4,
        mb: 4,
        px: { xs: 1, sm: 2, md: 4 },
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      {/* Date Selector */}
      <Box
        sx={{
          mb: 3,
          display: "flex",
          flexDirection: "row",
          overflowX: "auto",
          gap: 1,
          px: 1,
          width: "100%",
        }}
      >
        {getUpcomingDates(10).map((dateObj) => {
          const dateStr = dateObj.toISOString().split("T")[0];
          const dayName = dateObj.toLocaleDateString("en-US", { weekday: "short" });
          const dayNumber = dateObj.getDate();
          const isSelected = selectedDate === dateStr;
          const isAvailable = availableDates.includes(dateStr);

          return (
            <Box
              key={dateStr}
              onClick={() => isAvailable && setSelectedDate(dateStr)}
              sx={{
                minWidth: 60,
                cursor: isAvailable ? "pointer" : "not-allowed",
                p: 1.5,
                textAlign: "center",
                borderRadius: 2,
                border: isSelected ? "2px solid #1976d2" : "1px solid #ccc",
                bgcolor: isSelected
                  ? "primary.main"
                  : isAvailable
                  ? "white"
                  : "grey.200",
                color: isSelected
                  ? "white"
                  : isAvailable
                  ? "text.primary"
                  : "text.disabled",
                flexShrink: 0,
                pointerEvents: isAvailable ? "auto" : "none",
              }}
            >
              <Typography variant="body2">{dayName}</Typography>
              <Typography variant="h6" fontWeight="bold">
                {dayNumber}
              </Typography>
            </Box>
          );
        })}
      </Box>

      {/* Error Message */}
      {error && (
        <Box sx={{ display: "flex", justifyContent: "center", my: 5 }}>
          <Card sx={{ maxWidth: 400, p: 3, textAlign: "center" }}>
            <Typography variant="h6" color="error" fontWeight="bold">
              ⚠️ Error
            </Typography>
            <Typography variant="body2" color="text.secondary" mt={1}>
              {error}
            </Typography>
          </Card>
        </Box>
      )}

      {/* No Shows Available */}
      {!error && noShows && (
        <Box sx={{ display: "flex", justifyContent: "center", my: 5 }}>
          <Card sx={{ maxWidth: 400, p: 3, textAlign: "center" }}>
            <Typography variant="h6" color="text.secondary" fontWeight="bold">
              🎬 No Shows Available
            </Typography>
            <Typography variant="body2" color="text.secondary" mt={1}>
              Please select a different date or check back later.
            </Typography>
          </Card>
        </Box>
      )}

      {/* Theatres with Shows */}
      <Stack spacing={3} sx={{ width: "100%" }}>
        {movieShows.map(
          (group, groupIndex) =>
            group.length > 0 && (
              <Card
                variant="outlined"
                sx={{ borderRadius: 2, width: "100%" }}
                key={groupIndex}
              >
                <CardHeader
                  title={
                    <>
                      <Typography variant="h6" fontWeight="bold">
                        {group[0].theatreName}
                      </Typography>
                      <Typography fontWeight="normal">
                        {group[0].theatreLocation}
                      </Typography>
                    </>
                  }
                  sx={{
                    bgcolor: "primary.main",
                    color: "white",
                    borderRadius: "8px 8px 0 0",
                    textAlign: "center",
                  }}
                />
                <CardContent sx={{ bgcolor: "grey.100" }}>
                  <Grid container justifyContent="center" spacing={2}>
                    {group.map((movieShow, index) => {
                      const showDateTime = new Date(
                        `${movieShow.showDate}T${movieShow.showTime}`
                      );
                      const isPast = showDateTime.getTime() < Date.now();
                      const isSoldOut = movieShow.availableSeatsCount === 0;

                      return (
                        <Grid item xs={6} sm={4} md={2} key={index}>
                          <Box textAlign="center">
                            <Button
                              variant={
                                isPast || isSoldOut || role === "ADMIN"
                                  ? "outlined"
                                  : "contained"
                              }
                              color={isPast || isSoldOut ? "inherit" : "primary"}
                              onClick={() => handleShowSeatLayout(movieShow)}
                              disabled={isPast || isSoldOut || role === "ADMIN"}
                              sx={{ width: "100%", fontWeight: "bold" }}
                            >
                              {movieShow.showTime}
                            </Button>
                            <Box sx={{ mt: 1 }}>
                              {isPast ? (
                                <Chip label="Show Passed" color="default" size="small" />
                              ) : isSoldOut ? (
                                <Chip label="Sold Out" color="error" size="small" />
                              ) : (
                                <Chip
                                  label={`${movieShow.availableSeatsCount} Seats Left`}
                                  color="success"
                                  size="small"
                                />
                              )}
                            </Box>
                          </Box>
                        </Grid>
                      );
                    })}
                  </Grid>
                </CardContent>
              </Card>
            )
        )}
      </Stack>
    </Box>
  );
};

export default Bookings;
