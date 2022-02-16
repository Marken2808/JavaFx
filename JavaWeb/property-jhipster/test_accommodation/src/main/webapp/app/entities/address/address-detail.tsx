import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './address.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AddressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">Address</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="numberic">Numberic</span>
          </dt>
          <dd>{addressEntity.numberic}</dd>
          <dt>
            <span id="street">Street</span>
          </dt>
          <dd>{addressEntity.street}</dd>
          <dt>
            <span id="county">County</span>
          </dt>
          <dd>{addressEntity.county}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="postcode">Postcode</span>
          </dt>
          <dd>{addressEntity.postcode}</dd>
          <dt>
            <span id="country">Country</span>
          </dt>
          <dd>{addressEntity.country}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
