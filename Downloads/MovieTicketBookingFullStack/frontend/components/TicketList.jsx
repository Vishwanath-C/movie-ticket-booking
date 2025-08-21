import Ticket from "./Ticket";

const TicketList = ({ tickets }) => {
    return (
        <div className="row">
            {tickets.map(ticket => (
                <div className="col-md-6 mb-4" key={ticket.id}>
                    <Ticket  ticket={ticket} />
                </div>
            ))}
        </div>
    );
};

export default TicketList;