import React from  'react';
import moment from 'moment';
import PerformanceTable from "./PerformanceTable";
class PerformanceForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {date: '', place:'', noOfAvailableSeats:'', artist:''};
    }

    handleSelectedPerformance=(id, date, place, noOfSeats, artist) => {
        this.setState({date: date});
        this.setState({place: place});
        this.setState({noOfAvailableSeats: noOfSeats});
        this.setState({artist: artist});
        this.setState({idSelected: id});
    }

    handleDateChange=(event) =>{
        this.setState({date: event.target.value});
    }

    handlePlaceChange=(event) =>{
        this.setState({place: event.target.value});
    }

    handleSeatsChange=(event) =>{
        this.setState({noOfAvailableSeats: event.target.value});
    }

    handleArtistChange=(event) =>{
        this.setState({artist: event.target.value});
    }

    handleSubmit=(event) =>{
        const performance = {
            date: moment(this.state.date).format("yyyy-MM-DD HH:mm:00"),
            place: this.state.place,
            artist: this.state.artist,
            noOfAvailableSeats: this.state.noOfAvailableSeats,
            noOfSoldSeats: 0
        };
        console.log('A performance was submitted: ');
        console.log(performance);
        this.props.addFunc(performance);
        this.state.date = '';
        this.state.place = '';
        this.state.noOfAvailableSeats = '';
        this.state.artist = '';
        event.preventDefault();
    }

    handleClick=(event) =>{
        const performance = {
            id: this.state.idSelected,
            date: moment(this.state.date).format("yyyy-MM-DD HH:mm:00"),
            place: this.state.place,
            artist: this.state.artist,
            noOfAvailableSeats: this.state.noOfAvailableSeats,
            noOfSoldSeats: 0
        };
        console.log('A performance was updated: ');
        console.log(performance);
        this.props.updateFunc(performance);
        this.state.date = '';
        this.state.place = '';
        this.state.noOfAvailableSeats = '';
        this.state.artist = '';
        event.preventDefault();
    }

    render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <div className="form">
                        <label>Date:</label>
                        <input type="datetime-local" value={this.state.date} onChange={this.handleDateChange} />
                        <label>Place:</label>
                        <input type="text" value={this.state.place} onChange={this.handlePlaceChange} />
                    </div>
                    <div className="form">
                        <label>Number of seats:</label>
                        <input type="number" min={"1"} value={this.state.noOfAvailableSeats} onChange={this.handleSeatsChange} />
                        <label>Artist:</label>
                        <input type="text" value={this.state.artist} onChange={this.handleArtistChange} />
                    </div>
                    <div>
                        <input className="buttons" type="submit" value="Create performance" />
                        <button className="buttons" onClick={this.handleClick}>Update performance</button>
                    </div>
                </form>
                <PerformanceTable performances={this.props.performances}
                                  deleteFunc={this.props.deleteFunc} handleSelectFunc={this.handleSelectedPerformance}/>
            </div>
        );
    }
}
export default PerformanceForm;