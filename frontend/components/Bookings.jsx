import {
  Box,
  Button,
  Card,
  CardContent,
  CardHeader,
  Chip,
  CircularProgress,
  Grid,
  Stack,
  Typography,
} from "@mui/material";
import { useEffect, useState, useMemo } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import apiClient from "../src/api";

const Bookings = () => {
  const location = useLocation();
  const { movie } = location.state || {};

  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");
  const navigate = useNavigate();
  const { movieId } = useParams();

  // State
  const [showGroups, setShowGroups] = useState([]);
  const [showDates, setShowDates] = useState([]);
  const [activeDate, setActiveDate] = useState(() => {
    if (movie?.isUpcoming && movie?.availableShowDates?.length > 0) {
      return movie.availableShowDates[0];
    }
    return new Date().toISOString().split("T")[0];
  });

  const [fetchError, setFetchError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  // Derived state: no shows
  const hasNoShows =
    !Array.isArray(showGroups) ||
    showGroups.length === 0 ||
    showGroups.every((group) => group.length === 0);

  // Format show time
  const formatShowTime = (timeStr) => {
    const [hour, minutes] = timeStr.split(":").map(Number);
    const ampm = hour >= 12 ? "PM" : "AM";
    const hours12 = hour % 12 || 12;
    return `${hours12}:${minutes.toString().padStart(2, "0")} ${ampm}`;
  };

  // Generate upcoming dates (memoized)
  const generateUpcomingDates = useMemo(() => {
    return (numDays = 7) => {
      const dates = [];
      let startDate;

      if (movie?.isCurrentlyRunning) {
        startDate = new Date();
      } else if (movie?.isUpcoming && movie?.availableShowDates?.length > 0) {
        startDate = new Date(movie.availableShowDates[0]);
      } else {
        startDate = new Date();
      }

      for (let i = 0; i < numDays; i++) {
        const date = new Date(startDate);
        date.setDate(startDate.getDate() + i);
        dates.push(date);
      }

      return dates;
    };
  }, [movie]);

  // Load available show dates from movie
  useEffect(() => {
    if (movie?.availableShowDates) {
      setShowDates(movie.availableShowDates);

      if (movie.isUpcoming && movie.availableShowDates.length > 0) {
        setActiveDate(movie.availableShowDates[0]);
      }
    }
  }, [movie]);

  // Fetch shows whenever active date or movieId changes
  useEffect(() => {
    if (movieId && activeDate) {
      loadShowsForDate(activeDate);
    }
  }, [movieId, activeDate]);

  // Fetch shows for a given date
  const loadShowsForDate = async (date) => {
    setFetchError(null);
    setIsLoading(true);
    try {
      const response = await apiClient.get(
        `/movies/${movieId}/shows/date/${date}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setShowGroups(response.data);
    } catch (err) {
      console.error("Error fetching shows:", err);
      setFetchError("Failed to load movie shows. Please try again.");
    } finally {
      setIsLoading(false);
    }
  };

  // Navigate to seat layout
  const goToSeatLayout = (selectedShow) => {
    navigate("/app/seatlayout", { state: { selectedShow } });
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
      {/* Show Date Selector */}
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
        {generateUpcomingDates(10).map((dateObj) => {
          const dateStr = dateObj.toISOString().split("T")[0];
          const dayName = dateObj.toLocaleDateString("en-US", { weekday: "short" });
          const dayNumber = dateObj.getDate();
          const month = dateObj.toLocaleString("en-US", { month: "short" });
          const isSelected = activeDate === dateStr;
          const isAvailable = showDates.includes(dateStr);

          return (
            <Box
              key={dateStr}
              role="button"
              aria-label={`Select date ${dayName}, ${month} ${dayNumber}`}
              onClick={() => {
                if (isAvailable) {
                  setFetchError(null); // Clear error when switching date
                  setActiveDate(dateStr);
                }
              }}
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
              <Typography variant="body2">{month}</Typography>
            </Box>
          );
        })}
      </Box>

      {/* Loading Spinner */}
      {isLoading && (
        <Box sx={{ display: "flex", justifyContent: "center", my: 5 }}>
          <CircularProgress />
        </Box>
      )}

      {/* Error with Retry */}
      {fetchError && !isLoading && (
        <Box sx={{ display: "flex", justifyContent: "center", my: 5 }}>
          <Card sx={{ maxWidth: 400, p: 3, textAlign: "center" }}>
            <Typography variant="h6" color="error" fontWeight="bold">
              ⚠️ Error
            </Typography>
            <Typography variant="body2" color="text.secondary" mt={1}>
              {fetchError}
            </Typography>
            <Button
              variant="contained"
              sx={{ mt: 2 }}
              onClick={() => loadShowsForDate(activeDate)}
            >
              Retry
            </Button>
          </Card>
        </Box>
      )}

      {/* No Shows */}
      {!fetchError && !isLoading && hasNoShows && (
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

      {/* Theatre Show Groups */}
      {!isLoading && !fetchError && (
        <Stack spacing={3} sx={{ width: "100%" }}>
          {showGroups.map(
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
                      {group.map((show, index) => {
                        const showDateTime = new Date(
                          `${show.showDate}T${show.showTime}`
                        );
                        const isPast = showDateTime.getTime() < Date.now();
                        const isSoldOut = show.availableSeatsCount === 0;

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
                                onClick={() => goToSeatLayout(show)}
                                disabled={isPast || isSoldOut || role === "ADMIN"}
                                sx={{ width: "100%", fontWeight: "bold" }}
                              >
                                {formatShowTime(show.showTime)}
                              </Button>
                              <Box sx={{ mt: 1 }}>
                                {isPast ? (
                                  <Chip
                                    label="Show Passed"
                                    color="default"
                                    size="small"
                                  />
                                ) : isSoldOut ? (
                                  <Chip
                                    label="Sold Out"
                                    color="error"
                                    size="small"
                                  />
                                ) : (
                                  <Chip
                                    label={`${show.availableSeatsCount} Seats Left`}
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
      )}
    </Box>
  );
};

export default Bookings;
