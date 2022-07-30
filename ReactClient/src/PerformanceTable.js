import React from  'react';
import './PerformanceApp.css'
import moment from 'moment';

class PerformanceRow extends React.Component{
    handleClick=(event)=> {
        console.log('Delete button for: ' + this.props.performance.id);
        this.props.deleteFunc(this.props.performance.id);
    }

    handleRowClicked=(event)=> {
        console.log('ID selected: ' + this.props.performance.id);
        this.props.selectFunc(this.props.performance.id,
            moment(this.props.performance.date).format('yyyy-MM-DDTHH:mm'),
            this.props.performance.place,
            this.props.performance.noOfAvailableSeats + this.props.performance.noOfSoldSeats,
            this.props.performance.artist);
    }

    render() {
        return (
            <tr onClick={this.handleRowClicked}>
                <td className="elems">{this.props.performance.id}</td>
                <td className="elems">{this.props.performance.date}</td>
                <td className="elems">{this.props.performance.place}</td>
                <td className="elems">{this.props.performance.artist}</td>
                <td className="elems">{this.props.performance.noOfAvailableSeats}</td>
                <td className="elems"><button className="buttons" onClick={this.handleClick}>Delete</button></td>
            </tr>
        );
    }
}

class PerformanceTable extends React.Component {
    render() {
        console.log(this.props)
        const rows = [];
        const funcDel = this.props.deleteFunc;
        const funcSelect = this.props.handleSelectFunc;
        this.props.performances.forEach(function(performance) {
            rows.push(<PerformanceRow performance={performance} key={performance.id}
                                      deleteFunc={funcDel} selectFunc={funcSelect} />);
        });
        return (<div className="PerformanceTable">

                <table className="center">
                    <thead>
                    <tr>
                        <th className="headers">ID</th>
                        <th className="headers">Date</th>
                        <th className="headers">Place</th>
                        <th className="headers">Artist</th>
                        <th className="headers">No. of available seats</th>
                        <th className="headers"></th>
                    </tr>
                    </thead>
                    <tbody className="body">{rows}</tbody>
                </table>
            </div>
        );
    }
}

export default PerformanceTable;