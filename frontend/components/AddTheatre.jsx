import { useState } from "react";
import apiClient from "../src/api";
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
} from "@mui/material";

const AddTheatre = () => {
  const [name, setName] = useState("");
  const [location, setLocation] = useState("");
  const token = localStorage.getItem("token");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await apiClient.post(
        "/theatres/create-theatre",
        { name, location },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setName("");
      setLocation("");
      console.log(response.data);
    } catch (error) {
      console.error("Failed to add theatre:", error);
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4, borderRadius: 3, backgroundColor: "#f9fafb" }}>
        <Box>
          <Typography variant="h5" align="center" gutterBottom fontWeight="bold">
            Add a Theatre
          </Typography>
        </Box>

        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
          <TextField
            fullWidth
            label="Theatre Name"
            placeholder="Enter theatre name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            margin="normal"
          />

          <TextField
            fullWidth
            label="Location"
            placeholder="Enter location"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
            required
            margin="normal"
          />

          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            sx={{ mt: 3, borderRadius: 2 }}
          >
            Add Theatre
          </Button>
        </Box>
      </Paper>
    </Container>
  );
};

export default AddTheatre;
