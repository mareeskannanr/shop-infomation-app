import React, { Component } from 'react';

import axios from './axios';

import CsvModal from './CsvModal';
import ShopTable from './ShopTable';
import Message from './Message';
import Search from './Search';

export default class App extends Component {

  state = {
    open: false,
    shopInfos: [],
    result: [],
    error: ''
  };

  constructor() {
    super();
    axios.get('/shop-infos')
      .then(result => this.closeModal(false, result.data))
      .catch(() => this.closeModal(true, `Sorry, Something went wrong!`));
  }

  closeModal(error, data) {
    this.setState({
      open: false,
      error: error ? data : '',
      result: !error && data ? data: [],
      shopInfos: !error && data ? data: []
    });
  }

  filterShopByDate(date) {
    if(date) {
      date = date.setHours(0, 0, 0, 0);
      let result = this.state.result.filter(item => {
        let startDate = new Date(item.start_date).setHours(0, 0, 0, 0);
        let endDate = new Date(item.end_date).setHours(0, 0, 0, 0);

        return startDate <= date && endDate >= date;
      });

      return this.setState({
        shopInfos: result
      });
    }

    return this.setState({
        shopInfos: this.state.result
      });
  }

  render() {
    return (
      <div className='row'>
        <div className='col-12' style={{"marginTop": "20px"}}>
          <div className="alert alert-dark" role="alert">
            <h2>
              <span>
                <span className="fas fa-list"></span> Shop Informations
              </span>
              <button type="button" className="btn btn-success float-right" onClick={() => this.setState({open: true})}>
                <span className="fas fa-file-csv"></span> Import
              </button> 
            </h2>
          </div>
        </div>
        <div className='col-12' style={{"marginTop": "10px"}}>
          {this.state.result.length > 0 && <Search filerShop={date => this.filterShopByDate(date)} />}
        </div>
        <div className='col-12 text-center' style={{"marginTop": "15px"}}>
          { this.state.error ? <Message data={this.state.error} /> : this.state.shopInfos && this.state.shopInfos.length > 0 ? <ShopTable shopInfos={this.state.shopInfos} />  : <Message data="" />} 
        </div>
        {this.state.open ? <CsvModal closeModal={(error, message) => this.closeModal(error, message)} /> : ''}
      </div>
    );
  }
}