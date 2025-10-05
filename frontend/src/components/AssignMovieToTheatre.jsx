import CloseIcon from "@mui/icons-material/Close";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import {
  Alert,
  Box,
  Button,
  Container,
  Divider,
  FormControl,
  IconButton,
  InputLabel,
  MenuItem,
  Paper,
  Select,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TextField,
  Tooltip,
  Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import apiClient from "../src/api";
import CreateMovieShow from "./CreateMovieShow";

const AssignMovieToTheatre = () => {
  const token = localStorage.getItem("token");

  const [theatres, setTheatres] = useState([]);
  const [selectedTheatre, setSelectedTheatre] = useState(null);
  const [movies, setMovies] = useState([]);
  const [selectedMovie, setSelectedMovie] = useState(null);

  const [showTimes, setShowTimes] = useState([]);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [showsPerDay, setShowsPerDay] = useState(0);

  const [successAlert, setSuccessAlert] = useState({ show: false, msg: "" });
  const [errorAlert, setErrorAlert] = useState({ show: false, msg: "" });

  useEffect(() => {
    fetchTheatres();
  }, []);

  const tomorrow = new Date(Date.now() + 24 * 60 * 60 * 1000);
  const minDate = tomorrow.toISOString().split("T")[0];

  const fetchTheatres = async () => {
    try {
      const response = await apiClient.get("/theatres", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setTheatres(response.data || []);
    } catch (error) {
      console.error("Error fetching theatres:", error);
    }
  };

  const fetchMovies = async () => {
    try {
      const response = await apiClient.get("/movies", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMovies(response.data || []);
    } catch (error) {
      console.error("Error fetching movies:", error);
    }
  };

  const handleShowTimesUpdate = (newShowTimes) => setShowTimes(newShowTimes);

  const handleDeleteTime = (indexToRemove) =>
    setShowTimes((prev) => prev.filter((_, i) => i !== indexToRemove));

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!selectedTheatre || !selectedMovie) return;

    if (!startDate || !endDate || showTimes.length === 0) {
      setErrorAlert({ show: true, msg: "Please fill all fields and add showtimes." });
      setTimeout(() => setErrorAlert({ show: false, msg: "" }), 3000);
      return;
    }

    try {
      await apiClient.post(
        "/schedules",
        {
          theatreId: selectedTheatre.id,
          movieId: selectedMovie.id,
          numberOfShowsPerDay: showsPerDay,
          showTimings: showTimes,
          startDate,
          endDate,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setSuccessAlert({
        show: true,
        msg: `Movie "${selectedMovie.title}" assigned to "${selectedTheatre.name}" successfully!`,
      });
      setTimeout(() => setSuccessAlert({ show: false, msg: "" }), 3000);

      setSelectedTheatre(null);
      setSelectedMovie(null);
      setStartDate(null);
      setEndDate(null);
      setShowTimes([]);
      setShowsPerDay(0);
    } catch (error) {
      console.error("Error assigning movie:", error);
      setErrorAlert({ show: true, msg: "Failed to assign the movie. Please try again." });
      setTimeout(() => setErrorAlert({ show: false, msg: "" }), 3000);
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4, borderRadius: 3, backgroundColor: "#f9fafb" }}>
        <Box>
          <Typography variant="h5" align="center" gutterBottom fontWeight="bold">
            Assign movie
          </Typography>
        </Box>

        <Divider sx={{ mb: 3 }} />

        <Box component="form" onSubmit={handleSubmit}>
          <Stack spacing={3} sx={{ width: "100%" }}>
            {/* Theatre Select */}
            <FormControl fullWidth variant="outlined">
              <InputLabel>Select theatre</InputLabel>
              <Select
                value={selectedTheatre?.id ?? ""}
                onChange={(e) => {
                  const theatre = theatres.find((t) => t.id === e.target.value) || null;
                  setSelectedTheatre(theatre);
                  setSelectedMovie(null);
                  setShowTimes([]);
                  fetchMovies();
                }}
                fullWidth
                label="Select theatre"

              >
                {theatres.map((theatre) => (
                  <MenuItem key={theatre.id} value={theatre.id}>
                    {theatre.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            {/* Movie Select */}
            {selectedTheatre && (
              <FormControl fullWidth variant="outlined">
                <InputLabel>Select movie</InputLabel>
                <Select
                  value={selectedMovie?.id ?? ""}
                  onChange={(e) => {
                    const movie = movies.find((m) => m.id === e.target.value) || null;
                    setSelectedMovie(movie);
                    setShowTimes([]);
                  }}
                  label="Select movie"
                >
                  {movies.map((movie) => (
                    <MenuItem key={movie.id} value={movie.id}>
                      {movie.title}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            )}

            {/* Dates & Shows */}
            {selectedMovie && (
              <>
                <TextField
                  label="Start Date"
                  type="date"
                  fullWidth
                  value={startDate || ""}
                  onChange={(e) => setStartDate(e.target.value)}
                  InputLabelProps={{ shrink: true }}
                  inputProps={{ min: minDate }}
                  required
                />
                <TextField
                  label="End Date"
                  type="date"
                  fullWidth
                  value={endDate || ""}
                  onChange={(e) => setEndDate(e.target.value)}
                  InputLabelProps={{ shrink: true }}
                  inputProps={{ min: startDate }}
                  required
                />

                <FormControl fullWidth variant="outlined">
                  <InputLabel>Shows per day</InputLabel>
                  <Select
                    value={showsPerDay || ""}
                    onChange={(e) => setShowsPerDay(Number(e.target.value))}
                    label="Shows per day"
                  >
                    <MenuItem value="">
                      <em>None</em>
                    </MenuItem>
                    {[1, 2, 3, 4].map((num) => (
                      <MenuItem key={num} value={num}>
                        {num}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                {/* Show Time Picker */}
                {showsPerDay > 0 && (
                  <CreateMovieShow
                    showTimes={showTimes}
                    setShowTimes={handleShowTimesUpdate}
                    maxShows={showsPerDay}
                  />
                )}

                {/* Showtimes Table */}
                {showTimes.length > 0 && (
                  <Paper variant="outlined">
                    <Table size="small">
                      <TableHead>
                        <TableRow>
                          <TableCell>#</TableCell>
                          <TableCell>Showtime</TableCell>
                          <TableCell align="right">Actions</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {showTimes.map((time, index) => (
                          <TableRow key={index}>
                            <TableCell>{index + 1}</TableCell>
                            <TableCell>{time}</TableCell>
                            <TableCell align="right">
                              <Tooltip title="Delete">
                                <IconButton onClick={() => handleDeleteTime(index)} size="small">
                                  <DeleteOutlineIcon />
                                </IconButton>
                              </Tooltip>
                            </TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </Paper>
                )}
              </>
            )}

            <Button
              type="submit"
              variant="contained"
              size="large"
              disabled={!selectedTheatre || !selectedMovie || !startDate || !endDate || showTimes.length === 0}
            >
              Add Movie Show
            </Button>

            {/* Inline Alerts */}
            {successAlert.show && (
              <Alert
                severity="success"
                action={
                  <IconButton size="small" onClick={() => setSuccessAlert({ show: false, msg: "" })}>
                    <CloseIcon fontSize="inherit" />
                  </IconButton>
                }
              >
                {successAlert.msg}
              </Alert>
            )}

            {errorAlert.show && (
              <Alert
                severity="error"
                action={
                  <IconButton size="small" onClick={() => setErrorAlert({ show: false, msg: "" })}>
                    <CloseIcon fontSize="inherit" />
                  </IconButton>
                }
              >
                {errorAlert.msg}
              </Alert>
            )}
          </Stack>
        </Box>
      </Paper>
    </Container>
  );
};

export default AssignMovieToTheatre;