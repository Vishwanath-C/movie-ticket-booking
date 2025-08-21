import apiClient from "../src/api";
import { useEffect, useState } from "react";
import { Dropdown, DropdownButton } from "react-bootstrap";
// import { showTimes } from "./CreateMovieShow";

import CreateMovieShow from "./CreateMovieShow";


const AssignMovieToTheatre = () => {

    const [theatres, setTheatres] = useState([]);
    const [selectedTheatre, setSelectedTheatre] = useState(null);
    const [movies, setMovies] = useState([]);
    const [selectedMovie, setSelectedMovie] = useState(null);
    const [showTime, setShowTime] = useState(null);
    const token = localStorage.getItem('token');
    const [showTimes, setShowTimes] = useState([]);

    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const today = new Date().toISOString().split('T')[0];

    const [showsPerDay, setShowsPerDay] = useState(0);

    useEffect(() => { fetchTheatres(); }, []);

    useEffect(() => {
        console.log("Updated showTimes:", showTimes);
    }, [showTimes]);





    const fetchTheatres = async () => {
        // e.preventDefault();

        try {
            const response = await apiClient.get('/theatres/get-all-theatres', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setTheatres(response.data);
            console.log(theatres);
        } catch (error) {
            console.error("Error fetching theatres:", error);
        }

    };



    const fetchMovies = async () => {
        // e.preventDefault();

        try {
            const response = await apiClient.get('/movies/all-movies', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setMovies(response.data);
        } catch (error) {
            console.error("Error fetching movies : ", error);
        }
    };

    const handleAddShowTime = async (e) => {
        e.preventDefault();

        try {
            setShowTimes([...showTimes, showTime]);
            setShowTime(null);
        } catch (error) {
            console.log("Error while creating showtime : ", error);
        }
    };

    const handleUpdate = (newShowTimes) => {
        setShowTimes(newShowTimes);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await apiClient.post('/movie-assignments/create-assignment', {
                theatreId: selectedTheatre.id, movieId: selectedMovie.id, numberOfShowsPerDay: showsPerDay,
                showTimings: showTimes, startDate, endDate
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            setSelectedTheatre(null);
            setSelectedMovie(null);
            setStartDate(null);
            setEndDate(null);
            setShowTimes(null);

        } catch (error) {

        }
    };

    return (
        <div className="container border border-dark rounded lg-light mt-4 p-4 w-50">
            <h3 className="text-center fw-bold mb-4">Assign</h3>

            <form onSubmit={handleSubmit}>
                <div className="d-flex gap-4">
                    <label className="form-label fw-bold p-2">Select theatre</label>
                    <DropdownButton
                        id="dropdown-theatre"
                        title={selectedTheatre ? selectedTheatre.name : " -- Choose a theatre -- "}
                        variant="light"
                        className=" text-dark border border-dark rounded mb-4">
                        {theatres.map((theatre) => (
                            <Dropdown.Item
                                key={theatre.id}
                                onClick={() => { setSelectedTheatre(theatre); fetchMovies(); }}>
                                {theatre.name}
                            </Dropdown.Item>
                        ))}
                    </DropdownButton>
                </div>

                {selectedTheatre &&
                    <div className="">
                        <div className="d-flex gap-2">
                            <label className="form-label fw-bold p-2">Select movie</label>
                            <DropdownButton
                                id="dropdown-movie"
                                title={selectedMovie ? selectedMovie.title : " -- Choose a movie -- "}
                                variant="light"
                                className="border border-dark rounded text-dark mb-4">
                                {movies.map((movie) => (
                                    <Dropdown.Item
                                        key={movie.id}
                                        onClick={() => { setSelectedMovie(movie); }}>
                                        {movie.title}
                                    </Dropdown.Item>
                                ))}
                            </DropdownButton>
                        </div>


                    </div>
                }

                {selectedMovie && <div className="d-flex gap-2 mb-4">
                    <div>
                        <div className="d-flex gap-2 mb-4">
                            <label for="startDate" className="form-label fw-bold p-2">Select start date :</label>
                            <input type="date" className="form-control" id="startDate" name="startDate" min={today}
                                value={startDate} onChange={e => setStartDate(e.target.value)} required />
                        </div>

                        <div className="d-flex gap-2 mb-4">
                            <label for="endDate" className="form-label fw-bold p-2">Select end date : </label>
                            <input type="date" className="form-control" id="endDate" name="endDate" min={today}
                                value={endDate} onChange={e => setEndDate(e.target.value)} required />
                        </div>

                        <div className="d-flex mb-4">
                            <label className="fw-bold">Select number of shows per day : </label>
                            <select
                                id="showCount"
                                value={showsPerDay}
                                onChange={e => setShowsPerDay(e.target.value)}
                                className="form-select text-center border border-dark">
                                <option value=""> -- select shows per day -- </option>
                                {[1, 2, 3, 4].map((num) => (
                                    <option key={num} value={num}>{num}</option>
                                )
                                )}
                            </select>
                        </div>

                        <CreateMovieShow showTimes={showTimes} setShowTimes={handleUpdate} />

                        {showTimes && <div className="border border-dark rounded p-2">
                            <p className="fw-bold text-center"> Shows : </p>

                            {showTimes.map((showTime, index) => (
                                <div key={index} className="text-center">
                                    {showTime}
                                </div>
                            ))}

                        </div>}



                        {/* <div>
                            <label className="form-label fw-bold p-2">Select showtime  </label>
                            <input type="time" className="border border-dark rounded p-2" value={showTime}
                                onChange={e => setShowTime(e.target.value)} required />
                        </div> */}
                    </div>
                </div>}

                {<button type="submit" className="btn btn-primary d-block mx-auto">Add movie show</button>}

            </form>

        </div>
    );
};

export default AssignMovieToTheatre;