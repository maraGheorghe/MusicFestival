import React from  'react';
import PerformanceTable from "./PerformanceTable";
import './PerformanceApp.css'
import PerformanceForm from "./PerformanceForm";
import {GetAllPerformances, DeletePerformance, CreatePerformance, UpdatePerformance} from './utils/rest-calls'

class PerformanceApp extends React.Component{
  constructor(props){
    super(props);
    this.state = {performances:[],
      deleteFunc:this.deleteFunc.bind(this),
      addFunc:this.addFunc.bind(this),
      updateFunc:this.updateFunc.bind(this)
    }
    console.log('PerformanceApp constructor')
  }

  addFunc(performance){
    console.log('Inside create function: ' + performance);
    CreatePerformance(performance)
        .then(res => GetAllPerformances())
        .then(performances => this.setState({performances}))
        .catch(error =>console.log('create error: ', error));
  }


  deleteFunc(performance){
    console.log('Inside delete function: ' + performance);
    DeletePerformance(performance)
        .then(res => GetAllPerformances())
        .then(performances =>this.setState({performances}))
        .catch(error=>console.log('delete error: ', error));
  }

  updateFunc(performance){
    console.log('Inside update function: ' + performance);
    UpdatePerformance(performance)
         .then(res => GetAllPerformances())
         .then(performances =>this.setState({performances}))
         .catch(error=>console.log('update error: ', error));
    }

  componentDidMount() {
    console.log('Inside componentDidMount')
    GetAllPerformances().then(performances =>this.setState({performances}));
  }

  render(){
    return(
        <div className="PerformanceApp">
          <h1>Music Festival Performances Management</h1>
          <PerformanceForm addFunc={this.state.addFunc} performances={this.state.performances}
                           deleteFunc={this.state.deleteFunc} updateFunc={this.state.updateFunc} />
        </div>
    );
  }
}

export default PerformanceApp;
