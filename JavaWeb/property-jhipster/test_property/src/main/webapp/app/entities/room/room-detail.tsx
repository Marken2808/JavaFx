import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './room.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const roomEntity = useAppSelector(state => state.room.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roomDetailsHeading">
          <Translate contentKey="testPropertyApp.room.detail.title">Room</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{roomEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="testPropertyApp.room.title">Title</Translate>
            </span>
          </dt>
          <dd>{roomEntity.title}</dd>
          <dt>
            <span id="acreage">
              <Translate contentKey="testPropertyApp.room.acreage">Acreage</Translate>
            </span>
          </dt>
          <dd>{roomEntity.acreage}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="testPropertyApp.room.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {roomEntity.image ? (
              <div>
                {roomEntity.imageContentType ? (
                  <a onClick={openFile(roomEntity.imageContentType, roomEntity.image)}>
                    <img src={`data:${roomEntity.imageContentType};base64,${roomEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {roomEntity.imageContentType}, {byteSize(roomEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="type">
              <Translate contentKey="testPropertyApp.room.type">Type</Translate>
            </span>
          </dt>
          <dd>{roomEntity.type}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="testPropertyApp.room.price">Price</Translate>
            </span>
          </dt>
          <dd>{roomEntity.price}</dd>
          <dt>
            <Translate contentKey="testPropertyApp.room.accommodation">Accommodation</Translate>
          </dt>
          <dd>{roomEntity.accommodation ? roomEntity.accommodation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/room" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room/${roomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomDetail;
