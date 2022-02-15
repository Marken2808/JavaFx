import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './room.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Room = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const roomList = useAppSelector(state => state.room.entities);
  const loading = useAppSelector(state => state.room.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="room-heading" data-cy="RoomHeading">
        <Translate contentKey="testPropertyApp.room.home.title">Rooms</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="testPropertyApp.room.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="testPropertyApp.room.home.createLabel">Create new Room</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {roomList && roomList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="testPropertyApp.room.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.room.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.room.acreage">Acreage</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.room.image">Image</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.room.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.room.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.room.accommodation">Accommodation</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {roomList.map((room, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${room.id}`} color="link" size="sm">
                      {room.id}
                    </Button>
                  </td>
                  <td>{room.title}</td>
                  <td>{room.acreage}</td>
                  <td>
                    {room.image ? (
                      <div>
                        {room.imageContentType ? (
                          <a onClick={openFile(room.imageContentType, room.image)}>
                            <img src={`data:${room.imageContentType};base64,${room.image}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {room.imageContentType}, {byteSize(room.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`testPropertyApp.RoomType.${room.type}`} />
                  </td>
                  <td>{room.price}</td>
                  <td>{room.accommodation ? <Link to={`accommodation/${room.accommodation.id}`}>{room.accommodation.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${room.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${room.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${room.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="testPropertyApp.room.home.notFound">No Rooms found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Room;
