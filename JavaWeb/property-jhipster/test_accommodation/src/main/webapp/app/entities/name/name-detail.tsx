import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './name.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const NameDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const nameEntity = useAppSelector(state => state.name.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nameDetailsHeading">Name</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{nameEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{nameEntity.title}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{nameEntity.firstName}</dd>
          <dt>
            <span id="middleName">Middle Name</span>
          </dt>
          <dd>{nameEntity.middleName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{nameEntity.lastName}</dd>
          <dt>
            <span id="displayName">Display Name</span>
          </dt>
          <dd>{nameEntity.displayName}</dd>
        </dl>
        <Button tag={Link} to="/name" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/name/${nameEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NameDetail;
