import React, { useEffect, useState } from 'react';
import { Table, Button, Form, Row, Col, Pagination } from 'react-bootstrap';
import api from '../api';

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [search, setSearch] = useState('');
  const [searchType, setSearchType] = useState('title');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [sort, setSort] = useState({ field: 'title', direction: 'asc' });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchBooks();
  }, [search, searchType, page, sort]);

  const fetchBooks = async () => {
    try {
      const queryParams = new URLSearchParams();
      if (search) {
        queryParams.append(searchType, search);
      }
      queryParams.append("page", page);
      queryParams.append("sort", `${sort.field},${sort.direction}`);

      const response = await api.get(`/Books?${queryParams.toString()}`);
      setBooks(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error("Error fetching books:", error);
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
  };
  
  const handleSort = (field) => {
    if (sort.field === field) {
      const direction = sort.direction === 'asc' ? 'desc' : 'asc';
      setSort({ field, direction });
    } else {
      setSort({ field, direction: 'asc' });
    }
  };
  

  const handlePageChange = (selectedPage) => {
    setPage(selectedPage - 1);
  };

  const renderPaginationItems = () => {
    return Array.from({ length: totalPages }, (_, i) => (
      <Pagination.Item key={i + 1} active={i + 1 === page + 1} onClick={() => handlePageChange(i + 1)}>
        {i + 1}
      </Pagination.Item>
    ));
  };

  if (error) {
    return <div>Error fetching books: {error.message}</div>;
  }

  return (
    <div>
      <Form onSubmit={handleSearch}>
      <Row>
        <Col sm={10}>
          <Form.Control
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search books by title or author"
          />
        </Col>
        <Col sm={2}>
          <Button type="submit">Search</Button>
        </Col>
      </Row>
      <Form.Group controlId="searchType">
        <Form.Check
          type="radio"
          label="Title"
          name="searchType"
          value="title"
          checked={searchType === 'title'}
          onChange={(e) => setSearchType(e.target.value)}
        />
        <Form.Check
          type="radio"
          label="Author"
          name="searchType"
          value="author"
          checked={searchType === 'author'}
          onChange={(e) => setSearchType(e.target.value)}
        />
      </Form.Group>
    </Form>
    
      {loading ? (
        <div>Loading...</div>
      ) : (
        <>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>
                  <Button variant="link" onClick={() => handleSort('title')}>
                    Title {sort.field === 'title' && (sort.direction === 'asc' ? '▲' : '▼')}
                  </Button>
                </th>
                <th>
                  <Button variant="link" onClick={() => handleSort('author')}>
                    Author {sort.field === 'author' && (sort.direction === 'asc' ? '▲' : '▼')}
                  </Button>
                </th>
                <th>
                  <Button variant="link" onClick={() => handleSort('year')}>
                    Year {sort.field === 'year' && (sort.direction === 'asc' ? '▲' : '▼')}
                  </Button>
                </th>
              </tr>
            </thead>
            <tbody>
              {books.map((book) => (
                <tr key={book.id}>
                  <td>{book.title}</td>
                  <td>{book.author}</td>
                  <td>{book.year}</td>
                </tr>
              ))}
            </tbody>
          </Table>
          <Pagination>{renderPaginationItems()}</Pagination>
        </>
      )}
    </div>
  );
  };
  
  export default BookList;
  