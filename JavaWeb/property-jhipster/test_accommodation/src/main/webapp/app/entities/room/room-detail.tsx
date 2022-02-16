import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
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
        <h2 data-cy="roomDetailsHeading">Room</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{roomEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{roomEntity.title}</dd>
          <dt>
            <span id="acreage">Acreage</span>
          </dt>
          <dd>{roomEntity.acreage}</dd>
          <dt>
            <span id="image">Image</span>
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
            <span id="type">Type</span>
          </dt>
          <dd>{roomEntity.type}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{roomEntity.price}</dd>
          <dt>Accommodation</dt>
          <dd>{roomEntity.accommodation ? roomEntity.accommodation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/room" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room/${roomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomDetail;
