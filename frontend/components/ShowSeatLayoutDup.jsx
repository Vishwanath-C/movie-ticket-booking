import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import apiClient from "../src/api";
import { getCurrentUser } from "../src/utils/auth";

import screen from '../src/assets/screen.png';

const ShowSeatLayoutDup = () => {
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
    }, [seats]);

    const handleSeatClick = (seat) => {
        setSelectedSeats((prev) => {
            const isSelected = prev.includes(seat);

            const newSeats = isSelected
                ? prev.filter((s) => s !== seat)
                : [...prev, seat];

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
            const response = await apiClient.post('/tickets/create-ticket',
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
            setTimeout(() => setAlert(a => ({ ...a, show: false })), 3000);

        } catch (error) {

        }
    };



    return (

        <div className="">
            <h2 className="text-center mb-4 fw-bold">SEAT LAYOUT</h2>

            <div className="border border-dark rounded mb-4 p-4">
                <h5 className="fw-bold text-center mb-3">Gold seats -  Price : ₹{
                    Object.values(normalSeats).length > 0
                        ? Object.values(goldSeats)[0][0]?.price
                        : "No gold seats available"
                }</h5>

                {Object.entries(goldSeats).map(([, seatGroup], index) => (
                    <div key={index}>

                        <div className="d-flex gap-2 mb-2 flex-wrap justify-content-center">
                            <div className=" p-2  fw-bold text-center"
                                style={{ width: '40px', height: '40px' }}>
                                <h6> {seatGroup[0].seatNumber.charAt(1)}</h6>
                            </div>

                            {seatGroup.map((goldSeat, seatIndex) => (
                                <div className="text-center"
                                    key={goldSeat.id || seatIndex}>
                                    {goldSeat.booked ?
                                        <div className="border p-2 bg-secondary fw-bold"
                                            style={{
                                                cursor: 'pointer',
                                                backgroundColor: '#6c757d',
                                                width: '40px',
                                                height: '40px',
                                                aspectRatio: '1 / 1',
                                                color: 'white'
                                            }}                             >
                                            {goldSeat.seatNumber.slice(2)}

                                        </div>
                                        :
                                        <div
                                            // className="border p-2 bg-light fw-bold"
                                            className={`border p-2 fw-bold d-flex align-items-center justify-content-center
                                                ${goldSeat.booked ? 'bg-secondary text-white'
                                                    : selectedSeats.includes(goldSeat) ? 'bg-success text-white'
                                                        : 'bg-light text-dark'}`}
                                            style={{
                                                width: '40px',
                                                height: '40px',
                                                cursor: "pointer"
                                            }}
                                            onClick={() => handleSeatClick(goldSeat)}>
                                            {goldSeat.seatNumber.slice(2)}
                                        </div>
                                    }
                                </div>
                            ))}
                        </div>
                    </div>
                ))}

            </div>

            <div className="border border-dark rounded mb-4 p-4">
                <h5 className="fw-bold text-center mb-3">Normal seats -  Price : ₹{
                    Object.values(normalSeats).length > 0
                        ? Object.values(normalSeats)[0][0]?.price
                        : "No normal seats available"
                }</h5>

                {Object.entries(normalSeats).map(([, seatGroup], index) => (
                    <div key={index}>

                        <div className="d-flex gap-2 mb-2 flex-wrap justify-content-center">
                            <div className=" p-2  fw-bold text-center"
                                style={{ width: '40px', height: '40px' }}>
                                <h6> {seatGroup[0].seatNumber.charAt(1)}</h6>
                            </div>

                            {seatGroup.map((normalSeat, seatIndex) => (
                                <div className="text-center"
                                    key={normalSeat.id || seatIndex}>
                                    {normalSeat.booked ?
                                        <div className="border p-2 bg-secondary fw-bold"
                                            style={{
                                                cursor: 'pointer',
                                                backgroundColor: '#6c757d',
                                                width: '40px',
                                                height: '40px',
                                                aspectRatio: '1 / 1',
                                                color: 'white'
                                            }}                             >
                                            {normalSeat.seatNumber.slice(2)}

                                        </div>
                                        :
                                        <div
                                            // className="border p-2 bg-light fw-bold"
                                            className={`border p-2 fw-bold d-flex align-items-center justify-content-center
                                                ${normalSeat.booked ? 'bg-secondary text-white'
                                                    : selectedSeats.includes(normalSeat) ? 'bg-success text-white'
                                                        : 'bg-light text-dark'}`}
                                            style={{
                                                width: '40px',
                                                height: '40px',
                                                cursor: "pointer"
                                            }}
                                            onClick={() => handleSeatClick(normalSeat)}>
                                            {normalSeat.seatNumber.slice(2)}
                                        </div>
                                    }
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

export default ShowSeatLayoutDup;