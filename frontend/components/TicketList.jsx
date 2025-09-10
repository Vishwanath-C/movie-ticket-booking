import { Grid } from "@mui/material";
import Ticket from "./Ticket";

const TicketList = ({ tickets }) => {
  return (
    <Grid container spacing={3}>
      {tickets.map((ticket) => (
        <Grid item xs={12} sm={6} md={4} key={ticket.id}>
          <Ticket ticket={ticket} />
        </Grid>
      ))}
    </Grid>
  );
};

export default TicketList;
