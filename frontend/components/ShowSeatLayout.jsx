import apiClient from "../src/api";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { getCurrentUser } from "../src/utils/auth";

import availableSeat from '../src/assets/available_seat.jpg';
import bookedSeat from '../src/assets/booked_seat.jpg';
import selectedSeat from '../src/assets/selected_seat.jpg';
import screen from '../src/assets/screen.png';

const ShowSeatLayout = () => {
    const token = localStorage.getItem('token');
    const [user, setUser] = useState(null);
    const [seats, setSeats] = useState([]);
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [selectedTicketCount, setSelectedTicketCount] = useState(0);
    const [showAlert, setShowAlert] = useState(false);
    const [totalPrice, setTotalPrice] = useState(0);

    const [goldSeats, setGoldSeats] = useState([]);
    const [normalSeats, setNormalSeats] = useState([]);

    const [goldFirstLetter, setGoldFirstLetter] = useState(null);
    const [normalFirstLetter, setNormalFirstLetter] = useState(null);


    const { state } = useLocation();
    const movieShow = state?.selectedMovieShow;

    useEffect(() => { console.log("Movie Show :  ", movieShow) }, [movieShow]);

    useEffect(() => {
        setUser(getCurrentUser());
        if (movieShow?.showSeats) {
            setSeats(movieShow.showSeats);
        }
    }, [movieShow]);

    useEffect(() => {
        console.log("USER : ", user);
    }, [user]);

    useEffect(() => {
        console.log("Seats : ", seats);
    }, [seats]);

    useEffect(() => {

        const gold = {};
        const normal = {};
        seats.forEach(seat => {
            const row = seat.seatNumber.charAt(1);

            if (seat.seatNumber.charAt(0) === 'G') {
                if (!gold[row]) {
                    gold[row] = [];
                }
                gold[row].push(seat);
            }

            if (seat.seatNumber.charAt(0) === 'N') {
                if (!normal[row]) {
                    normal[row] = [];
                }
                normal[row].push(seat);
            }
        })

        setGoldSeats(gold);
        setNormalSeats(normal);

        console.log("Gold : ", gold);
        console.log("Normal : ", normal);


    }, [seats]);

    useEffect(() => {
        console.log("Updated gold seats: ", goldSeats);
        // console.log("Booked : ", goldSeats.at(0));
    }, [goldSeats]);

    useEffect(() => {
        console.log("selected seats : ", selectedSeats);
        console.log("Total price : ", totalPrice);
    }, [selectedSeats, totalPrice]);



    const handleSeatClick = (seat) => {
        setSelectedSeats((prev) => {
            const isSelected = prev.includes(seat);

            const newSeats = isSelected
                ? prev.filter((s) => s !== seat)
                : [...prev, seat];

            // Update total price
            const newTotalPrice = isSelected
                ? totalPrice - seat.price
                : totalPrice + seat.price;
            setTotalPrice(newTotalPrice);


            return newSeats;
        })
    };

    const [alert, setAlert] = useState({ show: false, msg: "", variant: "success" });

    const handleBookSeats = async () => {
        try {
            const response = await apiClient.post('/tickets',
                {
                    showSeatRequestDtos: selectedSeats, movieShowId: movieShow.id,
                    totalPrice, email: user.sub
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

            console.log("RESPONSE TICKET : ", response.data);
            setAlert({ show: true, msg: "Ticket created successfully!", variant: "success" });
            // optional: auto-hide after 3s
            setTimeout(() => setAlert(a => ({ ...a, show: false })), 3000);

        } catch (error) {

        }
    };



    return (

        <div className="">
            <h1 className="text-center mb-4 fw-bold">Seat Layout</h1>

            <div className="d-flex gap-4 mb-4 p-4 justify-content-center">
                <div className="text-center mx-4">
                    <img src={availableSeat} alt="availableSeat" className="img-fluid"
                        style={{ width: '50px' }} />
                    <h6 className="fw-bold p-2">Available</h6>
                </div>

                <div className="text-center mx-4">
                    <img src={selectedSeat} alt="selectedSeat" className="img-fluid"
                        style={{ width: '50px' }} />
                    <h6 className="fw-bold p-2">Selected</h6>
                </div>

                <div className="text-center mx-4">
                    <img src={bookedSeat} alt="bookedSeat" className="img-fluid"
                        style={{ width: '50px' }} />
                    <h6 className="fw-bold p-2">Booked</h6>
                </div>
            </div>

            {/* code to render gold seats */}
            <div className="border border-dark rounded p-4 mb-4">
                <h5 className="fw-bold text-center mb-3">Gold seats  -  Price : ₹{
                    Object.values(goldSeats).length > 0
                        ? Object.values(goldSeats)[0][0]?.price
                        : "No gold seats available"
                }</h5>
                {Object.entries(goldSeats).map(([, seatGroup], index) => (
                    <div key={index} >

                        <div className="d-flex gap-2 justify-content-center">
                            {seatGroup.map((goldSeat, seatIndex) => (
                                <div className="text-center"
                                    key={goldSeat.id || seatIndex}>
                                    {goldSeat.booked ?
                                        <img src={bookedSeat} alt="bookedSeat"
                                            className="img-fluid" style={{ width: '40px' }} /> :
                                        <img src={selectedSeats.includes(goldSeat) ? selectedSeat : availableSeat}
                                            alt="availableSeat" className="img-fluid"
                                            style={{ width: '40px' }}
                                            onClick={() => handleSeatClick(goldSeat)} />
                                    }

                                    <h6 className="fw-bold p-2"> {goldSeat.seatNumber}</h6>
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>

            <div className="border border-dark rounded p-4 mb-4">
                <h5 className="fw-bold text-center mb-3">Normal seats</h5>

                {Object.entries(normalSeats).map(([, seatGroup], index) => (
                    <div key={index}>
                        <div className="d-flex gap-2 flex-wrap justify-content-center">
                            {seatGroup.map((normalSeat, seatIndex) => (
                                <div className="text-center"
                                    key={normalSeat.id || seatIndex}>
                                    {normalSeat.booked ?
                                        <img src={bookedSeat} className="img-fluid"
                                            style={{ width: '40px' }} /> :
                                        <img src={selectedSeats.includes(normalSeat) ? selectedSeat : availableSeat} alt="availableSeat"
                                            className="img-fluid"
                                            style={{ width: '40px' }}
                                            onClick={() => handleSeatClick(normalSeat)} />}
                                    <h6 className="fw-bold p-2"> {normalSeat.seatNumber}</h6>
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>

            <div className="text-center mb-4">
                <img src={screen} alt="" style={{ transform: "rotate(180deg)" }} />
            </div>

            <div className="border border-dark rounded mb-4 p-4">
                <h5 className="fw-bold">Selected seats : </h5>
                <div className="d-flex gap-4">
                    {selectedSeats.map((selectedSeat) => (
                        <h5>{selectedSeat.seatNumber}</h5>
                    ))}
                </div>
                <div className="mb-4"></div>
                <h5 className="fw-bold">Total price : ₹{totalPrice} </h5>
            </div>

            <div>
                {selectedSeats.length > 0 && <button className="btn btn-primary" onClick={() => handleBookSeats()}>Book</button>}
                {alert.show && (
                    <div className={`alert alert-${alert.variant} alert-dismissible fade show mt-3`} role="alert">
                        {alert.msg}
                        <button type="button" className="btn-close" onClick={() => setAlert(a => ({ ...a, show: false }))} aria-label="Close"></button>
                    </div>
                )}
            </div>
        </div >
    );
};

export default ShowSeatLayout;