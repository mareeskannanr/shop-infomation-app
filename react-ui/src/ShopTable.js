import React, { Component } from 'react';

export default class ShopTable extends Component {

    headerArray = ["Shop", "Start Date", "End Date"];

    render() {
        return (
            <div className="row">
                <div className="col">
                    <table className="table table-striped">
                        <thead className="table-dark">
                            <tr>
                                {this.headerArray.map(header => <th key={header}>{header}</th>)}
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.props.shopInfos.map((row, index) =>
                                    <tr key={index}>
                                        <td>{row.shop}</td>
                                        <td>{row.start_date}</td>
                                        <td>{row.end_date}</td>
                                    </tr>
                                )    
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }

}