import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import axios from './axios';

export default class CsvModal extends Component {

    state = {
        selectedFile: '',
        success: false
    }

    uploadFile() {
        const data = new FormData();
        data.append('file', this.state.selectedFile, this.state.selectedFile.name);
    
        axios.post('/upload', data).then(
            () => {
                this.setState({success: true});
                setTimeout(this.getShopInfos.bind(this), 1500);
            }    
        ).catch(
            err => this.props.closeModal(true, (err.response && err.response.data) || `Sorry, Something went wrong!`)
        );
    }

    getShopInfos() {
        axios.get('/shop-infos').then(
            result => this.props.closeModal(false, result.data)
        ).catch(
            this.props.closeModal(true, `Sorry, Something went wrong!`)
        );
    }

    render() {
        return (
            <Modal isOpen={true}>
                <ModalHeader><span className="fas fa-file-upload"></span> Upload CSV</ModalHeader>
                <ModalBody>
                    <div className="col">
                        {this.state.success ? <h5 className="text-success">Shop Infos Uploaded Successfully!</h5> : (
                            <div className="col-8 offset-2 form-group">
                                <input type='file' onChange={e => this.setState({selectedFile: e.target.files[0] })} className="form-control" />
                            </div>)}
                    </div>
                </ModalBody>
                <ModalFooter>
                    {!this.state.success && <div className="col-12 text-center">
                        <button type="button" className="btn btn-success" onClick={() => this.uploadFile()} disabled={!this.state.selectedFile}>
                            <span className="fas fa-upload"></span> Upload
                        </button>&nbsp;&nbsp;
                        <button type="button" className="btn btn-danger" onClick={() => this.props.closeModal()}>
                            <span className="fas fa-times"></span> Close
                        </button>
                    </div>}
                </ModalFooter>
            </Modal>
        );
    }

}