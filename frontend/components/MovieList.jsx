
import { Box, Container, Grid, Typography } from "@mui/material";
import MovieCard from "./MovieCard";

const MovieList = ({ movies }) => {
  return (
    <Container>
      
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
          width: "100%"
        }}
      >
        <Typography variant="h6" fontWeight="bold" align="center">
          Movies
        </Typography>
      </Box>
      <Grid container spacing={3}>
        {movies.map((movie) => (
          <Grid item xs={12} sm={6} md={4} key={movie.id}>
            <MovieCard movie={movie} />
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default MovieList;
