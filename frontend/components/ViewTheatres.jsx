import { useEffect, useState } from "react";
import apiClient from "../src/api";
import TheatreCard from "./TheatreCard";

// MUI imports
import { Container, Typography, Grid, CircularProgress, Alert, Box } from "@mui/material";

export default function ViewTheatres() {
  const [theatres, setTheatres] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const token = localStorage.getItem("token");

  const getTheatres = async () => {
    try {
      const response = await apiClient.get("/theatres/get-all-theatres", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setTheatres(response.data);
      console.log(theatres);
    } catch (err) {
      console.error("Failed to fetch theatres:", err);
      setError("Could not load theatres. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getTheatres();
  }, []);

  return (
    <Container maxWidth="lg" sx={{ mt: 6, textAlign: "center" }}>
      <Box
        sx={{
          display: "inline-block",
          bgcolor: "primary.main",
          color: "white",
          px: 3,
          py: 1,
          borderRadius: 2,
          boxShadow: 3,
          mb: 4,
          width:"100%"
        }}
      >
        <Typography variant="h6" fontWeight="bold" align="center">
          Theatres
        </Typography>
      </Box>

      {loading && (
        <Grid container justifyContent="center" sx={{ mt: 4 }}>
          <CircularProgress />
        </Grid>
      )}

      {error && (
        <Alert severity="error" sx={{ mt: 2 }}>
          {error}
        </Alert>
      )}

      {!loading && !error && (

        <Grid container justifyContent="center" spacing={3}>
          {theatres.map((theatre) => (
            <Grid item key={theatre.id}>
              <TheatreCard theatre={theatre} />
            </Grid>
          ))}
        </Grid>

      )}
    </Container>
  );
}
