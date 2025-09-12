
const AlertMessage = ({ show, variant = "warning", msg, onClose }) => {
  if (!show) return null;

  return (
    <div
      className={`alert alert-${variant} alert-dismissible fade show mt-3`}
      role="alert"
    >
      {msg}
      <button
        type="button"
        className="btn-close"
        onClick={onClose}
        aria-label="Close"
      ></button>
    </div>
  );
};

export default AlertMessage;
