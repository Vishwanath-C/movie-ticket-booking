import apiClient from "../src/api";
import { useEffect, useState } from "react";
import { getCurrentUser } from "../src/utils/auth";
import TicketList from "./TicketList";



const MyBookings = () => {
    const [ticketsVisible, setTicketsVisible] = useState(false);
    const token = localStorage.getItem('token');
    const [ticketsUpcoming, setTicketsUpcoming] = useState([]);
    const [ticketsFinished, setTicketsFinished] = useState([]);

    useEffect(() => { handleShowTickets(); }, []);

    const ticketFinished = () => {
        const date = new Date(ticket.showDate);
        const currentDate = new Date();

        if (currentDate > date) return true;
        else {
            // const showTime = ticket.showTime;
            // const currentTime = new
        }
    };

    const handleShowTickets = async () => {
        const user = getCurrentUser();

        try {
            const response = await apiClient.get(
                "/tickets/get-tickets",
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

            const allTickets = response.data;
            const finished = [];
            const upcoming = [];

            allTickets.forEach(ticket => {
                const dateTimeString = `${ticket.showDate}T${ticket.showTime}`
                const showDateTime = new Date(dateTimeString);
                const currentDate = new Date();

                if(showDateTime < currentDate) finished.push(ticket);
                else upcoming.push(ticket);
            });
            
            setTicketsUpcoming(upcoming);
            setTicketsFinished(finished);
            setTicketsVisible(true);
        } catch (error) {
            console.log(error);
        }
    };

    // const handleShowTickets = async () => {
    //     const user = getCurrentUser();

    //     try {
    //         const response = await apiClient.get(
    //             "/tickets/get-tickets",
    //             {
    //                 headers: {
    //                     Authorization: `Bearer ${token}`,
    //                 },
    //             });

    //         const allTickets = response.data;
    //         const finished = [];
    //         const upcoming = [];

    //         allTickets.forEach(ticket => {
    //             const showDate = new Date(ticket.showDate);
    //             const currentDate = new Date();

    //             if(showDate < currentDate) finished.push(ticket);
    //             else if(showDate > currentDate) upcoming.push(ticket);
    //         });
    //         setTicketsUpcoming(upcoming);
    //         setTicketsFinished(finished);
    //         setTicketsVisible(true);
    //     } catch (error) {
    //         console.log(error);
    //     }
    // };

    return (
        <>
            {/* <div className="container border border-dark rounded bg-light p-4 mb-4 w-50"> */}
            <div>
                {/* <button className="btn btn-primary d-block mx-auto mb-4" onClick={handleShowTickets}>Refresh</button> */}
                <h3 className="text-center">UPCOMING SHOWS</h3>
                <div className="container mt-4 px-0 mb-4" >
                    
                    {ticketsVisible && <TicketList tickets={ticketsUpcoming} />}
                </div>
            </div>

            <div>
                <h3 className="text-center">FINISHED SHOWS</h3>
                <div className="container mt-4 px-0 mb-4" >
                    
                    {ticketsVisible && <TicketList tickets={ticketsFinished} />}
                </div>
            </div>
        </>
    )
};

export default MyBookings;