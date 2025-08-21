import ticketBackground from '../src/assets/ticketBackground.png';

const Ticket = ({ ticket }) => {

    const convertTo12Hour = (time24) => {
        const [hourStr, minute, second] = time24.split(':');
        let hour = parseInt(hourStr, 10);
        const ampm = hour >= 12 ? 'PM' : 'AM';
        hour = hour % 12;
        if (hour === 0) hour = 12;
        return `${hour.toString().padStart(2, '0')}:${minute} ${ampm}`;
    };



    return (
        <div

            className="card h-100 shadow-sm bg-danger">
            <div className="card-body">
                <div className="bg-white text-center p-2 border rounded">
                    <h4 className="fw-bold">{ticket.movie_name}</h4>
                </div>
                <div className="card p-3 shadow-sm mb-2">
                    <table>
                        <tbody>
                            <tr>
                                <td><p className="fw-semibold mb-0">Theatre   </p></td>
                                <td>{ticket.theatre_name}</td>
                            </tr>
                            <tr>
                                <td><p className="fw-semibold mb-0">Date   </p></td>
                                <td>{ticket.showDate}</td>
                            </tr>
                            <tr>
                                <td><p className="fw-semibold mb-0">Time  </p></td>
                                <td>{convertTo12Hour(ticket.showTime)}</td>
                            </tr>
                            <tr>
                                <td><p className="fw-semibold mb-0">Total Price</p></td>
                                <td>₹{ticket.totalPrice}</td>
                            </tr>
                            <tr>
                                <td><p className="fw-semibold mb-0">Seat Numbers</p></td>
                                <td>
                                    <div className="d-flex gap-2 flex-wrap">
                                        {(ticket.seatNumbers || []).map((s) => (
                                            <span key={s} className="badge bg-secondary">{s}</span>
                                        ))}
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>


        </div>
    );
};

export default Ticket;

