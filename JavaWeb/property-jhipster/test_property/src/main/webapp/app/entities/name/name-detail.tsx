import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
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
        <h2 data-cy="nameDetailsHeading">
          <Translate contentKey="testPropertyApp.name.detail.title">Name</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nameEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="testPropertyApp.name.title">Title</Translate>
            </span>
          </dt>
          <dd>{nameEntity.title}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="testPropertyApp.name.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{nameEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="testPropertyApp.name.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{nameEntity.middleName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="testPropertyApp.name.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{nameEntity.lastName}</dd>
          <dt>
            <span id="displayName">
              <Translate contentKey="testPropertyApp.name.displayName">Display Name</Translate>
            </span>
          </dt>
          <dd>{nameEntity.displayName}</dd>
        </dl>
        <Button tag={Link} to="/name" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/name/${nameEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NameDetail;
