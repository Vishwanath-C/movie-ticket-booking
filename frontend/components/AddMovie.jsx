import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
  Alert,
  IconButton,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { useEffect, useRef, useState } from "react";
import apiClient from "../src/api";

export default function AddMovieForm() {
  const role = localStorage.getItem("role");
  const token = localStorage.getItem("token");

  const [movieTitle, setMovieTitle] = useState("");
  const [movieDescription, setMovieDescription] = useState("");
  const [durationMinutes, setDurationMinutes] = useState("");

  const [successAlert, setSuccessAlert] = useState({ show: false, msg: "" });
  const [duplicateAlert, setDuplicateAlert] = useState({ show: false, msg: "" });

  const textareaRef = useRef(null);

  // Auto-expand textarea
  useEffect(() => {
    if (textareaRef.current) {
      textareaRef.current.style.height = "auto";
      textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
    }
  }, [movieDescription]);

  const [errors, setErrors] = useState({
    movieTitle: "",
    movieDescription: "",
    durationMinutes: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    let valid = true;
    const newErrors = { movieTitle: "", movieDescription: "", durationMinutes: "" };

    if (!movieTitle.trim()) {
      newErrors.movieTitle = "Title is required.";
      valid = false;
    }

    if (!movieDescription.trim()) {
      newErrors.movieDescription = "Description is required.";
      valid = false;
    }

    if (!durationMinutes || durationMinutes <= 0) {
      newErrors.durationMinutes = "Duration must be greater than 0.";
      valid = false;
    }

    setErrors(newErrors);

    if (!valid) return;

    try {
      await apiClient.post(
        "/movies",
        {
          title: movieTitle,
          description: movieDescription,
          duration: parseInt(durationMinutes, 10),
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setSuccessAlert({ show: true, msg: "Movie added successfully!" });
      setTimeout(() => setSuccessAlert({ show: false, msg: "" }), 3000);

      setMovieTitle("");
      setMovieDescription("");
      setDurationMinutes("");
      setErrors({ movieTitle: "", movieDescription: "", durationMinutes: "" });
    } catch (error) {
      setDuplicateAlert({
        show: true,
        msg: `Movie with name "${movieTitle}" already exists.`,
      });
      setTimeout(() => setDuplicateAlert({ show: false, msg: "" }), 5000);
    }
  };


  if (role !== "ADMIN") {
    return (
      <Alert severity="error" sx={{ mt: 4 }}>
        You are not authorized to add movies.
      </Alert>
    );
  }

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4, borderRadius: 3, backgroundColor: "#f9fafb" }}>
        <Box sx={{ mb: 3, textAlign: "center" }}>
          <Typography
            variant="h5"
            fontWeight="bold"
            sx={{
              display: "inline-block",
              borderBottom: 2,
              borderColor: "primary.main",
              pb: 1,
            }}
          >
            Add Movie
          </Typography>
        </Box>


        {successAlert.show && (
          <Alert
            severity="success"
            sx={{ mb: 2 }}
            action={
              <IconButton
                size="small"
                color="inherit"
                onClick={() => setSuccessAlert({ show: false, msg: "" })}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>
            }
          >
            {successAlert.msg}
          </Alert>
        )}

        {duplicateAlert.show && (
          <Alert
            severity="warning"
            sx={{ mb: 2 }}
            action={
              <IconButton
                size="small"
                color="inherit"
                onClick={() => setDuplicateAlert({ show: false, msg: "" })}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>
            }
          >
            {duplicateAlert.msg}
          </Alert>
        )}

        <Box component="form" onSubmit={handleSubmit}>
          <TextField
            label="Title"
            fullWidth
            margin="normal"
            value={movieTitle}
            onChange={(e) => setMovieTitle(e.target.value)}
            error={!!errors.movieTitle}
            helperText={errors.movieTitle || " "} // use space to reserve layout
          />

          <TextField
            label="Description"
            fullWidth
            margin="normal"
            multiline
            rows={1}
            inputRef={textareaRef}
            value={movieDescription}
            onChange={(e) => setMovieDescription(e.target.value)}
            error={!!errors.movieDescription}
            helperText={errors.movieDescription || " "}
          />

          <TextField
            label="Duration (minutes)"
            type="number"
            fullWidth
            margin="normal"
            inputProps={{ min: 1 }}
            value={durationMinutes}
            onChange={(e) => setDurationMinutes(e.target.value)}
            error={!!errors.durationMinutes}
            helperText={errors.durationMinutes || " "}
          />



          <Box sx={{ textAlign: "center" }}>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              sx={{ mt: 3, borderRadius: 1.5 }}
            >
              Add Movie
            </Button>

          </Box>
        </Box>
      </Paper>
    </Container>
  );
}
