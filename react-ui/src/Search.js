import React, { Component } from 'react';
import DatePicker from "react-datepicker";
 
import "react-datepicker/dist/react-datepicker.css";

export default class Search extends Component {

    constructor(props) {
        super(props);
        this.state = {
          date: ''
        };
    }

    render() {
        return (<div className="col-12 justify-content-center text-center">
            <DatePicker selected={this.state.date} onChange={date => this.setState({date})} className="form-control" />&nbsp;&nbsp;&nbsp;
            <button type="button" className="btn btn-primary" onClick={() => this.props.filerShop(this.state.date)}>
                <span className="fas fa-search"></span> Search
            </button>
        </div>);
    }

}