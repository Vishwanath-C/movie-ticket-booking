import { useEffect, useState } from "react";
import apiClient from "../src/api";
import MovieList from "./MovieList";
import EmptyState from "./EmptyState";
import { Container, Box } from "@mui/material";
import { LoadingButton } from "@mui/lab";

const MovieActions = ({ nowrunning }) => {

    const role = localStorage.getItem("role");
    const token = localStorage.getItem("token");

    // let url = role === 'ADMIN' ? "/movies/all-movies" : nowrunning ? "/movies/now-running" :
    //     "/movies/upcoming-movies";

    let url = nowrunning ? "/movies/now-running" :
        "/movies/upcoming";

    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (token && role) {
            fetchMovies();
        }
    }, [token, role, nowrunning]);


    const fetchMovies = async () => {
        setLoading(true);
        try {
             const response = await apiClient.get(url, {
                    headers: { Authorization: `Bearer ${token}` },
                });
            
            setMovies(response?.data || []);
        } catch (error) {
            console.error("Error fetching movies:", error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
            <Box>
                {loading ? (
                    <Box display="flex" justifyContent="center" mt={4}>
                        <LoadingButton loading variant="outlined">
                            Loading Movies
                        </LoadingButton>
                    </Box>
                ) : movies.length > 0 ? (
                    <MovieList movies={movies} />
                ) : (
                    <EmptyState heading="No movies" />
                )}
            </Box>
        </Container>
    );
};

export default MovieActions;
