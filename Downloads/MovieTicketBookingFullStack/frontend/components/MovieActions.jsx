import { useEffect, useState } from "react";
import apiClient from "../src/api";
import MovieList from "./MovieList";
import ShowSeatLayoutDup from "./ShowSeatLayoutDup";

const MovieActions = () => {
    const role = localStorage.getItem('role');
    const token = localStorage.getItem('token');
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [duration, setDuration] = useState('');
    const [movies, setMovies] = useState([]);
    const [moviesVisible, setMoviesVisible] = useState(false);
    const [seatLayoutView, setSeatLayoutView] = useState(false);

    useEffect(() => { handleShowMovies(); }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await apiClient.post('/movies/create-movie', {
                title,
                description,
                duration
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            setTitle('');
            setDescription('');
            setDuration('');

        } catch (error) {

        }
    };

    const handleShowMovies = async () => {
        console.log('Inside');
        try {
            const response = await apiClient.get('/movies/all-movies', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setMovies(response.data);
            setMoviesVisible(true);
        } catch (error) {

        }
    };

    return (
        <>
            {seatLayoutView ? <ShowSeatLayoutDup /> :
                (
                    <>
                        {role === 'ADMIN' &&
                            <div className="container mt-4 bg-light text-center border border-dark rounded p-4 mb-4">
                                <h3 className="fw-bold">Add a movie</h3>
                                <form onSubmit={handleSubmit}>
                                    <input className="form-control mb-4" type="text" placeholder="Enter movie title"
                                        value={title} onChange={(e) => setTitle(e.target.value)} required />

                                    <input className="form-control mb-4" type="text" placeholder="Enter the description"
                                        value={description} onChange={(e) => setDescription(e.target.value)} required />

                                    <input className="form-control mb-4" type="number" min={0} step={1} placeholder="Enter duration in minutes"
                                        value={duration} onChange={(e) => setDuration(parseInt(e.target.value, 10))} required />

                                    <button className="btn btn-primary" type="submit">Add movie</button>
                                </form>
                            </div>}

                        {/* <div className="container mt-4 p-4 mb-4" > */}
                        <div className="container mt-4 px-0 mb-4" >
                            {moviesVisible && <MovieList movies={movies} />}
                        </div>
                    </>
                )}
        </>
    );
};

export default MovieActions;