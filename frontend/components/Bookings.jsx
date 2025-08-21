import apiClient from "../src/api";
import { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import ShowSeatLayout from "./ShowSeatLayout";

const Bookings = () => {
    const [movieShows, setMovieShows] = useState([]);
    const token = localStorage.getItem('token');
    const [selectedMovieShow, setSelectedMovieShow] = useState(null);
    const [selectedShowView, setSelectedShowView] = useState(false);
    const [selectedDate, setSelectedDate] = useState(null);
    const navigate = useNavigate();
    const { movieId } = useParams();


    const location = useLocation();
    // const movie = location.state?.movie;
    const today = new Date().toISOString().split('T')[0];

    // useEffect(() => { handleDateSelection(today); }, []);

    useEffect(() => {
        if (movieId) {
            handleDateSelection(today, movieId);
        } else {
            // navigate("/");
        }
    }, [movieId, today]);


    const handleDateSelection = async (date) => {
        setSelectedDate(date);

        try {
            const response = await apiClient.get(`/movieshows/movies/${movieId}/date/${date}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setMovieShows(response.data);
        } catch (error) {
            console.error("Error fetching movie shows:", error);
        }
    };

    const handleShowSeatLayout = (selectedMovieShow) => {
        navigate('/app/seatlayout', { state: { selectedMovieShow } });
        // navigate(`/bookings/${movie.id}`);

    };



    return (
        <div>

            {/* <h3>Bookings screen</h3> */}

            <div className="mb-4">
                <label className="fw-bold p-2">Select date </label>
                <input type="date" className="border rounded p-2 border-dark" value={selectedDate || today}
                    onChange={(e) => { handleDateSelection(e.target.value); }} min={today}/>
            </div>

            {movieShows.map((group, groupIndex) => (
                (group.length > 0 && <div className="border border-dark bg-light p-4 w-50 rounded mb-4" key={groupIndex}>
                    {console.log("Group : ", group)}
                    <h4 className="mb-4 fw-bold">Theatre : {group[0].theatreName}, {group[0].theatreLocation}</h4>
                    <div className="d-flex gap-2">
                        {group.map((movieShow, index) => (

                            <div key={index}>
                                <button
                                    className="btn btn-primary"
                                    onClick={() => handleShowSeatLayout(movieShow)}
                                    disabled={movieShow.availableSeatsCount === 0 || 
                                        new Date(`${movieShow.showDate}T${movieShow.showTime}`) < new Date()
                                    }

                                >
                                    <div>
                                        {movieShow.showTime}
                                        
                                    </div>
                                </button>
                                <div>
                                    {(movieShow.availableSeatsCount === 0) && (
                                            <small className="d-block text-danger fw-bold">Sold Out</small>
                                        )}
                                </div>
                            </div>
                        ))}
                    </div>
                </div>)
            ))}

            {selectedShowView && <ShowSeatLayout selectedMovieShow={selectedMovieShow} />}

        </div>



    );
};

export default Bookings;