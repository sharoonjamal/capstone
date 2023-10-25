export default function HttpStatus({ message, status }) {
    return (
        <div className="text-center">
            <img src={`https://httpstatusdogs.com/img/${status}.jpg`} alt={`${status} ${message}`} />
        </div>
    );
}